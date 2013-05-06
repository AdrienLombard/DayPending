package fr.adrienlombard.clock;

public class Heure {

	private int heure, minutes;
	
	public Heure() {
		heure = 0;
		minutes = 0;
	}
	
	public Heure(int heure, int minutes) {
		this.heure = heure;
		this.minutes = minutes;
	}
	
	/**
	 * 
	 * @param h Heure au format "HH:MM"
	 */
	public Heure(String h) {
		this.heure = Integer.parseInt(h.substring(0, 2));
		this.minutes = Integer.parseInt(h.substring(3, 2));
	}
	
	public boolean estAvant(Heure h) {
		if(this.heure > h.getHeure()) {
			return false;
		}
		else if(this.heure == h.getHeure()) {
			if(this.minutes < h.getMinutes()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
	}
	
	public boolean estApres(Heure h) {
		if(this.heure > h.getHeure()) {
			return true;
		}
		else if(this.heure == h.getHeure()) {
			if(this.minutes < h.getMinutes()) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean equals(Heure h) {
		return (this.heure == h.getHeure() && this.minutes == h.getMinutes());
	}

	public int getHeure() {
		return heure;
	}

	public void setHeure(int heure) {
		this.heure = heure;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
}
