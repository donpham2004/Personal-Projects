package com.mazefinder;

public interface Direction {
	public int moveXDirection();
	public int moveYDirection();
}

enum Directions implements Direction {
	ONE{

		@Override
		public int moveXDirection() {
			return 1;
		}
		
		@Override
		public int moveYDirection() {
			return 0;
		}	
	},
	TWO{
		@Override
		public int moveXDirection() {
			return 0;
		}
		
		@Override
		public int moveYDirection() {
			return 1;
		}	
	},
	THREE{
		@Override
		public int moveXDirection() {
			return -1;
		}
		
		@Override
		public int moveYDirection() {
			return 0;
		}	
	},FOUR{
		@Override
		public int moveXDirection() {
			return 0;
		}
		
		@Override
		public int moveYDirection() {
			return -1;
		}	
	}
}