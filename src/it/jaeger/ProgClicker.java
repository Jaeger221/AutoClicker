package it.jaeger;

import javax.swing.JFrame;

import it.jaeger.utility.Clicker;

public class ProgClicker {
    public static void main(String[] args) {
		Clicker c = new Clicker();

    	c.setSize(300,200);
		c.setVisible(true);
		c.setResizable(false);
		c.setLocation(500,400);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
