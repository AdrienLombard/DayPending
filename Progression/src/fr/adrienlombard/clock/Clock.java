package fr.adrienlombard.clock;

import java.text.NumberFormat;
import java.util.Calendar;

class Clock {
	
	private Heure now;
	private Heure debut;
	private Heure fin;
	private Heure debutPause;
	private Heure finPause;
	private int secondesJournee;
	
	public Clock() {
		debut = new Heure(9, 0);
		fin = new Heure(17, 0);
		debutPause = new Heure(12, 30);
		finPause = new Heure(13, 30);
	}
	
	public String getProgression() {
		
		Calendar c = Calendar.getInstance();
		
		now = new Heure(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
		
		if(fin.estApres(debut)) {
			if(now.estAvant(debut)) {
				return "<html><p style=\"text-align:center;\">C'est encore l'heure du café /</p><p style=\"text-align:center;\">thé / chocolat / gnôle / chocapics</p><p style=\"text-align:center;\">(rayer les mentions inutiles)</p></html>";
			}
			else {
				if(now.estAvant(fin)) {
					if(now.estAvant(debutPause) || now.estApres(finPause)) {
						return "<html><p style=\"text-align:center;\">Au boulot !</p><p style=\"text-align:center;\">" + calculePourcent() + "% de la journée écoulés !</p></html>";
					}
					else {
						return "<html><p style=\"text-align:center;\">Manger !</p><p style=\"text-align:center;\">Pipipipi PIZZA !</p></html>";
					}
				}
				else {
					return "<html><p style=\"text-align:center;\">C'est fini ! T'as plus le temps !</p><p style=\"text-align:center;\">Sors ! Sors !</p></html>";
				}
			}
		}
		else {
			if ( now.estApres(fin) && now.estAvant(debut) ) {
				int heuresRepos = debut.getHeure() - fin.getHeure();
				if(fin.getHeure() + now.getHeure() >= heuresRepos / 2) {
					return "<html><p style=\"text-align:center;\">C'est encore l'heure du café /</p><p style=\"text-align:center;\">thé / chocolat / gnôle / chocapics</p><p style=\"text-align:center;\">(rayer les mentions inutiles)</p></html>";
				}
				else {
					return "<html><p style=\"text-align:center;\">C'est fini ! T'as plus le temps !</p><p style=\"text-align:center;\">Sors ! Sors !</p></html>";
				}
			}
			else {
				if(finPause.estAvant(debutPause)) {
					if(now.estApres(debutPause) && now.estApres(finPause)) {
						return "<html><p style=\"text-align:center;\">Manger !</p><p style=\"text-align:center;\">Pipipipi PIZZA !</p></html>";
					}
					else if(now.estAvant(debutPause) && now.estAvant(finPause)) {
						return "<html><p style=\"text-align:center;\">Manger !</p><p style=\"text-align:center;\">Pipipipi PIZZA !</p></html>";
					}
					else {
						return "<html><p style=\"text-align:center;\">Au boulot !</p><p style=\"text-align:center;\">" + calculePourcent() + "% de la journée écoulés !</p></html>";
					}
				}
				else {
					if(now.estAvant(debutPause) || now.estApres(finPause)) {
						return "<html><p style=\"text-align:center;\">Au boulot !</p><p style=\"text-align:center;\">" + calculePourcent() + "% de la journée écoulés !</p></html>";
					}
					else {
						return "<html><p style=\"text-align:center;\">Manger !</p><p style=\"text-align:center;\">Pipipipi PIZZA !</p></html>";
					}
				}
			}
		}
	
	}
	
	public String calculePourcent() {
		
		Calendar c = Calendar.getInstance();
		
		double secondesEcoulees = 0;
		
		if(fin.estApres(debut) || fin.equals(debut)) {
			secondesEcoulees = (c.get(Calendar.HOUR_OF_DAY) - debut.getHeure()) * Math.pow(60, 2) - debut.getMinutes() * 60 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
		}
		else {
			if(c.get(Calendar.HOUR_OF_DAY) >= debut.getHeure()) {
				secondesEcoulees = (c.get(Calendar.HOUR_OF_DAY) - debut.getHeure()) * Math.pow(60, 2) - debut.getMinutes() * 60 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
			}
			else {
				int avantMinuit = (int) ((23 - debut.getHeure()) * Math.pow(60, 2)) - debut.getMinutes() * 60 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
				int apresMinuit = (int)(c.get(Calendar.HOUR_OF_DAY) * Math.pow(60, 2) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND));
				secondesEcoulees = avantMinuit + apresMinuit;
			}
		}
		
		double pourcent = secondesEcoulees * 100 / secondesJournee;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(3);
		nf.setMaximumFractionDigits(3);
		return nf.format(pourcent);
	}
	
	public void calculeSecondesJournee() {
		
		int heuresJournee = 0;
		
		if(fin.estApres(debut) || fin.equals(debut)) {
			heuresJournee = fin.getHeure() - debut.getHeure();
		}
		else {
			heuresJournee = 24 - debut.getHeure() + fin.getHeure();
		}
		
		this.secondesJournee = (int) (heuresJournee * Math.pow(60, 2)) + (fin.getMinutes() - debut.getMinutes()) * 60;
		
	}

	public Heure getDebut() {
		return debut;
	}

	public void setDebut(Heure debut) {
		this.debut = debut;
	}

	public Heure getFin() {
		return fin;
	}

	public void setFin(Heure fin) {
		this.fin = fin;
	}

	public Heure getDebutPause() {
		return debutPause;
	}

	public void setDebutPause(Heure debutPause) {
		this.debutPause = debutPause;
	}

	public Heure getFinPause() {
		return finPause;
	}

	public void setFinPause(Heure finPause) {
		this.finPause = finPause;
	}
	
}