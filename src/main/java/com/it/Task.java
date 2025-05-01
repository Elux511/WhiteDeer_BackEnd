package com.it;

import java.time.LocalTime;
import java.util.Vector;

public class Task {
    enum CheckInMethod{
        faceRecognition,
        geoFencing,
        both
    };

    String id;
    String name;
    Group group;
    //QRcode
    LocalTime begin_time;
    LocalTime end_time;
    CheckInMethod method;
    Vector<String> yes_user;
    Vector<String> no_user;

}
