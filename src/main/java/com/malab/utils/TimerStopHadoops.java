package com.malab.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.TimerTask;

// Note: Stop Hadoops and Instances after 30 mins if no operation.
public class TimerStopHadoops extends TimerTask{
	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("timer.txt"));
			String status = br.readLine();
			if (status.equals("stopped")) {
				return;
			}
			long timer = Long.parseLong(br.readLine());
			if (System.currentTimeMillis()-timer > 20*60*1000) {
				if (!new PythonSDK().stop_hadoops()) {
					System.out.println("Instances starting fail, please contact administrator!");
				}
				/*if (!new PythonSDK().stop()) {
					System.out.println("Hadoops starting fail, please contact administrator!");
				}*/
				// ensure "stoppped"
				BufferedWriter bw = new BufferedWriter(new FileWriter("timer.txt"));
				bw.write("stopped");
				bw.close();
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
