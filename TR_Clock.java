import javax.swing.*;
import java.awt.*;
import java.util.Date;
public class TR_Clock extends JFrame{
	public int hour;
	public int min;
	public int sec;
	ClockDial cd;
	public TR_Clock()
	{
		setSize(510,530);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		cd=new ClockDial(this);
		getContentPane().add(cd);
		Date curr=new Date();
		String time=curr.toString();
		hour=Integer.parseInt(time.substring(11,13));
		min=Integer.parseInt(time.substring(14,16));
		sec=Integer.parseInt(time.substring(17,19));
		ClockEngine.setPriority(ClockEngine.getPriority()+3);
		ClockEngine.start();
		
	}
	
	
	
	public static void main(String[] args)
	{
		new TR_Clock().setVisible(true);
			
	
	}
	Thread ClockEngine=new Thread(){
		int newsec,newmin;
		public void run(){
			while(true)
			{
				newsec=(sec+1)%60;
				newmin=(min+(sec+1)/60)%60;
				hour=(hour+(min+(sec+1)/60)/60)%12;
				
				sec=newsec;
				min=newmin;
				try{
					Thread.sleep(1000);
				}
				catch(Exception e)
				{			
				}
				cd.repaint();
			}
		}
		
	};
}
class ClockDial extends JPanel{
	TR_Clock parent;
	public ClockDial(TR_Clock pt){
		setSize(520,530);
		parent=pt;		
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0,0,getWidth(),getHeight());
		Color myColor=new Color(200,200,200);
		g.setColor(myColor);
		g.fillOval(5,5,480,480);
		g.setColor(Color.black);		
		g.fillOval(10,10,470,470);
		g.setColor(myColor);
		g.fillOval(237,237,15,15);
		g.setFont(g.getFont().deriveFont(Font.BOLD,32));
		g.setColor(Color.white);
		for(int i=1;i<=12;i++)
			g.drawString(Integer.toString(i),240-(i/12)*11+(int)(210*Math.sin(i*Math.PI/6)),253-(int)(210*Math.cos(i*Math.PI/6)));
		
			g.setColor(myColor);
			double minsecdeg=(double)Math.PI/30;
			double hrdeg=(double)Math.PI/6;
			int tx,ty;
			int xpoints[]=new int[3];
			int ypoints[]=new int[3];
			tx=245+(int)(210*Math.sin(parent.sec*minsecdeg));
			ty=245-(int)(210*Math.cos(parent.sec*minsecdeg));
			g.drawLine(245,245,tx,ty);
			
			tx=245+(int)(190*Math.sin(parent.min*minsecdeg));
			ty=245-(int)(190*Math.cos(parent.min*minsecdeg));
			xpoints[0]=245;
			xpoints[1]=tx+2;
			xpoints[2]=tx-2;
			ypoints[0]=245;
			ypoints[1]=ty+2;
			ypoints[2]=ty-2;
			g.fillPolygon(xpoints,ypoints,3);
			
			tx=245+(int)(160*Math.sin(parent.hour*hrdeg+parent.min*Math.PI/360));
			ty=245-(int)(160*Math.cos(parent.hour*hrdeg+parent.min*Math.PI/360));

			xpoints[1]=tx+4;
			xpoints[2]=tx-2;

			ypoints[1]=ty+4;
			ypoints[2]=ty-2;
			g.fillPolygon(xpoints,ypoints,3);					
	}
}
