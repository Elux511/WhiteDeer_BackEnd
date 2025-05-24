import os
import sys
from io import BytesIO
import cv2 as cv
import numpy as np
from PIL import Image
import threading


#人脸检测模型
face_detector = cv.CascadeClassifier("haarcascade_frontalface_default.xml")

# 使用线程局部存储，为每个线程创建独立的识别器实例
thread_local = threading.local()

def get_recognizer():
    if not hasattr(thread_local, 'recognizer'):
        thread_local.recognizer = cv.face.LBPHFaceRecognizer_create()
    return thread_local.recognizer


#获得用户人脸的特征（用于初次记录）
#传入用户人脸图片，保存人脸特征
def getImageLabels(user_id, img_path):
    try:
        #获取人脸识别器
        recognizer = get_recognizer()

        #获取传入的用户人脸特征
        #img = cv.imread(img_path)
        PIL_image = Image.open(img_path).convert('L')
        img_numpy = np.array(PIL_image, 'uint8')
        faces = face_detector.detectMultiScale(img_numpy, 1.1, 5, 0)
        largest_face = np.array(1)

        #只取屏幕中最大的那张脸
        for x, y, w, h in faces:
            if w * h > largest_face.size:
                #cv.rectangle(img,(x,y),(x+w,y+h),color=(0,0,255),thickness=2)
                largest_face = img_numpy[y:y+h,x:x+w]
            else:
                continue

        #如果没有人脸
        if largest_face.size <=1:
            return False


        # cv.imshow("result",img)
        # cv.waitKey(0)
        # cv.destroyAllWindows()
        #打印最大的脸的大小
        # print(largest_face.size)

        #让识别器记录id和对应人脸
        recognizer.train([largest_face],np.array([int(user_id)]))
        if not os.path.exists("trainer"):
            os.makedirs("trainer")
        recognizer.write(f"trainer/{user_id}_trainer.yml")
        return True
    except Exception as e:
        return False


if __name__ == "__main__":

    if len(sys.argv) < 3:
        print("错误：参数不足")
        sys.exit(1)

    user_id, img_path = int(sys.argv[1]), sys.argv[2]
    success = getImageLabels(user_id, img_path)
    if success:
        print("true")
        sys.exit(0)
    else:
        print("false")
        sys.exit(1)