import sys

import cv2 as cv
import numpy as np
from PIL import Image


#人脸检测模型
face_detector = cv.CascadeClassifier("haarcascade_frontalface_default.xml")
#人脸识别器
recognizer = cv.face.LBPHFaceRecognizer_create()


#获得用户人脸的特征（用于初次记录）
#传入用户人脸图片，保存人脸特征
def getImageLabels(user_id, img_path):
    #为用户赋值id
    id = []
    face_sample = []

    #获取传入的用户人脸特征
    img = cv.imread(img_path)
    PIL_image = Image.open(img_path).convert('L')
    img_numpy = np.array(PIL_image, 'uint8')
    face = face_detector.detectMultiScale(img_numpy, 1.1, 5, 0)
    largest_face = np.array(1)

    #只取屏幕中最大的那张脸
    for x, y, w, h in face:
        if w * h > largest_face.size:
            #cv.rectangle(img,(x,y),(x+w,y+h),color=(0,0,255),thickness=2)
            largest_face = img_numpy[y:y+h,x:x+w]
        else:
            continue
    #为id和face_sample赋值
    id.append(user_id)
    face_sample.append(largest_face)

    # cv.imshow("result",img)
    # cv.waitKey(0)
    # cv.destroyAllWindows()
    #打印最大的脸的大小
    # print(largest_face.size)

    #让识别器记录id和对应人脸
    recognizer.train(face_sample,np.array(id))
    recognizer.write(f"trainer/{user_id}_trainer.yml")
    print(1)


if __name__ == "__main__":

    if len(sys.argv) < 3:
        print("错误：参数不足")
        sys.exit(1)

    user_id = int(sys.argv[1])
    img_path = sys.argv[2]
    getImageLabels(user_id, img_path)