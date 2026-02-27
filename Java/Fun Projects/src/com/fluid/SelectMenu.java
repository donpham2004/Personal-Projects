package com.fluid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class SelectMenu {
	private static LinkedList<Item> items = new LinkedList<>();
	private Handler handler;
	public SelectMenu(Handler handler) {
		items.add(new Item(1000,10,ID.SAND));
		items.add(new Item(1000,60,ID.WATER));
		items.add(new Item(1000,110,ID.STONE));
		items.add(new Item(1000,160,ID.SALT));
		items.add(new Item(1000,210,ID.OIL));
		items.add(new Item(1000,260,ID.FOUNDATION));
		items.add(new Item(1000,310,ID.TNT));
		items.add(new Item(1000,360,ID.FIRE));
		items.add(new Item(1000,410,ID.WOOD));
		items.add(new Item(1000,460,ID.ACID));
		items.add(new Item(1000,510,ID.FISH));
		this.handler=handler;
		items.add(new Clear(1000,560,handler));
	}
	
	public void render(Graphics g) {
		for(Item temp:items) {
			temp.render(g);
		}
	}
	
	public void tick() {
		
	}
	
	public Item getItem(int xPos, int yPos) {
		for(Item temp:items) {
			if(xPos>=temp.getxPos()&&temp.getxPos()+temp.getLength()>=xPos&&yPos>=temp.getyPos()&&temp.getyPos()+temp.getLength()>=yPos) {
				temp.selected();
				return temp;
			}
		}
		return null;
	}
}

class Item {
	
	protected int length = 40;
	protected int xPos, yPos;
	protected ID id;
	protected static Item selectedItem;
	public Item(int xPos, int yPos,ID id) {
		this.xPos = xPos;
		this.yPos=yPos;
		this.id=id;
	}
	
	public void render(Graphics g) {
		if(equals(selectedItem)) {
			g.setColor(Color.RED);
			g.fillRect(xPos-5, yPos-5, length+10, length+10);
		}
		g.setColor(id.getColor());
		g.fillRect(xPos, yPos, length, length);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	public void selected() {
		selectedItem = this;
	}
}

class Clear extends Item {
	private Handler handler;
	public Clear(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, null);
		this.handler = handler;
	}
	
	@Override
	public void render(Graphics g) {
		if(equals(selectedItem)) {
			g.setColor(Color.RED);
			g.fillRect(xPos-5, yPos-5, length+10, length+10);
		}
		g.setColor(Color.WHITE);
		g.drawRect(xPos, yPos, length, length);
		g.drawString("Erase", xPos+4, yPos+25);
	}
	@Override
	public void selected() {
		if(selectedItem==null) {
			super.selected();
		}else {
			handler.removeAll(selectedItem.id);
			selectedItem=this;
		}
		
		
	}
}
