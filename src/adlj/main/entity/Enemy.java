package adlj.main.entity;

import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import adlj.main.gui.Frame;

public class Enemy {
	public double x, y,dx,dy;
	public int width, height;
	public static List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
	public Enemy(double x, double y, int w, int h, double dx, double dy){
		this.x= x ;
		this.y = y;
		this.width = w;
		this.height = h;
		this.dx = dx;
		this.dy = dy;
		final Enemy e = this;
		enemies.add(e);
		new Thread(){
			public void run(){
				try{	
					Rectangle r = new Rectangle();
					Rectangle pr= new Rectangle();
					while(e.y < Frame.GameFrame.HEIGHT){
						e.y += e.dy;
						r.setBounds((int)e.x,(int)e.y,e.width,e.height);
						for(Projectile p: Projectile.projectiles){
							pr.setBounds((int)p.x,(int)p.y,p.width,p.height);
							if(r.intersects(pr)){
								enemies.remove(e);
								Projectile.projectiles.remove(p);
								this.join();
							}
						}
						Thread.sleep(2);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
}
