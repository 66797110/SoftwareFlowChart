package pers.sfc.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import pers.sfc.windows.MyFrame;

public class Main {
	public static void main(String[] args)
	   {
	      EventQueue.invokeLater(() ->
	         {
	            var frame = new MyFrame();
	            frame.setTitle("MyPainter");
	            frame.setSize(800,800);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	         });
	   }
}
