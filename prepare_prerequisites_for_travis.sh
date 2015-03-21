#!/usr/bin/env bash
touch vitinfo2/src/main/java/com/karthikb351/vitinfo2/Secret.java
echo "package com.karthikb351.vitinfo2.objects;

public class Secret {
    public static String backup_password = \"ANYSTRINGHERE\";
}" >  vitinfo2/src/main/java/com/karthikb351/vitinfo2/Secret.java
