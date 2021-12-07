package it.jaeger.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Clicker extends JFrame implements ActionListener, KeyListener
{
	private JPanel panelAll = new JPanel();
	private JTextField posx = new JTextField();
	private JTextField posy = new JTextField();
	private JTextField time = new JTextField();
	private JLabel posxNow = new JLabel();
	private JLabel posyNow = new JLabel();
	private JLabel labelPosx = new JLabel("Pos x");
	private JLabel labelPosy = new JLabel("Pos y");
	private JLabel labelTime = new JLabel("Time (ms)");
	private JLabel status = new JLabel("Status: idle");
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private JButton sync = new JButton("Sync");
	
	private boolean running = true;

	public Clicker() {
		super("Autoclicker");
		
		panelAll.setLayout(new GridLayout(6,2,5,5));

		panelAll.add(posxNow);
		panelAll.add(posyNow);
		panelAll.add(labelPosx);
		panelAll.add(posx);
		panelAll.add(labelPosy);
		panelAll.add(posy);
		panelAll.add(labelTime);
		panelAll.add(time);
		panelAll.add(status);
		panelAll.add(sync);
		panelAll.add(start);
		panelAll.add(stop);

		sync.addKeyListener(this);

		posx.setBorder(BorderFactory.createLineBorder(Color.black));
		posy.setBorder(BorderFactory.createLineBorder(Color.black));
		time.setBorder(BorderFactory.createLineBorder(Color.black));
		start.setBackground(Color.WHITE);
		start.addActionListener(this);
		stop.setBackground(Color.WHITE);
		stop.addActionListener(this);
		sync.setBackground(Color.WHITE);
		sync.addActionListener(this);

		getContentPane().add(panelAll);
		updatePos();
	}

	public void updatePos() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					posxNow.setText(MouseInfo.getPointerInfo().getLocation().x + "");
					posyNow.setText(MouseInfo.getPointerInfo().getLocation().y + "");
				}
			}
		});

		thread.start();
	}

	public void updateStatus() {
		if(running)
			status.setText("Status: running");
		else
			status.setText("Status: idle");
	}

	public void actionPerformed(ActionEvent e) {
		String p = e.getActionCommand();
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(running) {
					try {
						Robot r = new Robot();
						int button = InputEvent.BUTTON1_DOWN_MASK;
						r.mouseMove(Integer.parseInt(posx.getText()), Integer.parseInt(posy.getText()));
						r.mousePress(button);
						Thread.sleep(400);
						r.mouseRelease(button);
						Thread.sleep(Integer.parseInt(time.getText()));
					} 
					catch (Exception ex) {
						
					}
				}
				return;
			}
		});
		
		status.setText(p);
		if(p.equals("Start")) {
			running = true;
			updateStatus();
			thread.start();
		}

		if(p.equals("Stop")) {
			running = false;
			updateStatus();
		}

		if(p.equals("Sync"))
			status.setText("Press -s");
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == 80) {
			running = false;
			updateStatus();
		}

		if(keyCode == 83) {
			posx.setText(MouseInfo.getPointerInfo().getLocation().x + "");
			posy.setText(MouseInfo.getPointerInfo().getLocation().y + "");
		}
	}
	
}
