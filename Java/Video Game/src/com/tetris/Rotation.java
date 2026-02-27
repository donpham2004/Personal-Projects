package com.tetris;

public interface Rotation {
	public int rotate(int block);

}

enum Rotations implements Rotation {

	FOUR {
		public int rotate(int block) {
			switch(block) {
			case 2:
				return 8;
			case 5:
				return 2;
			case 6:
				return 6;
			case 7:
				return 10;
			case 8:
				return 14;
			case 10:
				return 7;
			default:
				return 5;
			}
		}

		

	},
	THREE {
		

		public int rotate(int block) {
			switch(block) {
			case 1:
				return 3;
			case 2:
				return 7;
			case 3:
				return 11;
			case 5: 
				return 2;
			case 6: 
				return 6;
			case 7:
				return 10;
			case 9:
				return 1;
			case 10:
				return 5;
			default:
				return 9;
			}
		}
	}, NONE {
		

		public int rotate(int block) {
			return 0;
		}
	}

}
