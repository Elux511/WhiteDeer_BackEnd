import os
import sys
from io import BytesIO
import cv2 as cv
import numpy as np
from PIL import Image


#人脸检测模型
face_detector = cv.CascadeClassifier("haarcascade_frontalface_default.xml")
#人脸识别器
recognizer = cv.face.LBPHFaceRecognizer_create()


#人脸识别（用于后续打卡）
#将传入的人脸图片与训练模型中的图片一一对照
def checkFace(user_id, img_path):

    model_path = f"trainer/{user_id}_trainer.yml"
    if not os.path.exists(model_path):
        print("模型文件未找到")
        return False
    try:
        recognizer.read(model_path)

        #img = cv.imread(img_path)
        PIL_image = Image.open(img_path).convert('L')
        img_numpy = np.array(PIL_image, 'uint8')
        face = face_detector.detectMultiScale(img_numpy, 1.1, 5, 0)

        #挑选出最大的人脸
        largest_face = np.array(1)
        for x, y, w, h in face:
            if w * h > largest_face.size:
                #cv.rectangle(img,(x,y),(x+w,y+h),color=(0,0,255),thickness=2)
                largest_face = img_numpy[y:y+h,x:x+w]
            else:
                continue
        face_sample = largest_face

        #打印最大的脸的大小
        #print(largest_face.size)
        # cv.imshow("result",img)
        # cv.waitKey(0)
        # cv.destroyAllWindows()
        #检验人脸是否匹配
        id, confidence = recognizer.predict(face_sample)
        return id == user_id and confidence <= 80
    except Exception as e:
        return False


if __name__ == "__main__":
    #共有两个参数，参数长度应为3
    if len(sys.argv) != 3:
        print("错误：参数个数应为2")
        sys.exit(1)
    try:
        user_id = int(sys.argv[1])
        img_path = sys.argv[2]
    except ValueError:
        print("错误：用户id必须为整数")
        sys.exit(2)
    if checkFace(user_id, img_path):
        print("True")
    else:
        print("False")

