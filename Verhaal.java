import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Verhaal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Koe koe = new Koe();
		koe.produceren(40);
		Melkbus melkbus = new Melkbus(500);
		Melkmachine machine = new Melkmachine();
		machine.laadMelkbus(melkbus);
		machine.Melken(koe);
		Boer boer = new Boer(10,machine);
		boer.addKoe(koe);
		boer.koeienhouden(20);
		boer.alleKoeienMelken();
		System.out.println(boer.aantalbussen()+ " volle melkbussen!");
		boer.koeienhouden(50);
		boer.alleKoeienMelken();
		boer.printkoeien();
		System.out.println(boer.aantalbussen()+ " volle melkbussen!");
		boer.koeienhouden(3000);
		boer.alleKoeienMelken();
		boer.printkoeien();
		System.out.println(boer.aantalbussen()+ " volle melkbussen!");
		
		Boer.melkprijs=2;
		boer.verkoopbussen();
		System.out.println(boer.getOmzet()+ " euro omzet!");
		
		
		boer.koeienhouden(50);
		boer.alleKoeienMelken();
		boer.printkoeien();
		System.out.println(boer.aantalbussen()+ " volle melkbussen!");
		boer.koeienhouden(30);
		boer.alleKoeienMelken();
		boer.printkoeien();
		System.out.println(boer.aantalbussen()+ " volle melkbussen!");
		
		Boer.melkprijs=1; /// melkprijs daalt flink
		boer.verkoopbussen();
		System.out.println(boer.getOmzet()+ " euro omzet!");
		
	}
}


class Boer{
	public static int melkprijs;
	private Melkmachine machine;
	private List<Koe> koeien;
	private List<Melkbus> vollebussen;
	private Random random;
	private int omzet;
	

	Boer(int aantalkoeien,Melkmachine machine) {
		random = new Random();
		this.koeien = new ArrayList<Koe>();
		this.vollebussen = new ArrayList<Melkbus>();
		for(int i=0;i<aantalkoeien;i++)
			this.koeien.add(new Koe(random.nextInt(6)+1));
		this.machine=machine;
	}


	void koeienhouden(int uren){
		for (Koe koe : this.koeien) {
			koe.produceren(uren);
		}
	}

	void checkBus() {
		if(machine.isDeBusVol()) {
			this.vollebussen.add(machine.verwijderMelkbus());
			this.machine.laadMelkbus(new Melkbus(200)); /// maak op magische wijze een nieuwe melkbus
		}
	}

	void alleKoeienMelken() {
		for (Koe koe : this.koeien) {
			this.machine.Melken(koe);
			checkBus();
		}
	}

	void addKoe(Koe koe) {
		this.koeien.add(koe);
	}

	int aantalbussen() {
		return this.vollebussen.size();

	}

	void printkoeien() {
		for (Koe koe : this.koeien) {
			System.out.println("Koe heeft nog " + koe.getMelk() + " liter melk.");
		}
	}
	
	void verkoopbussen() {
		for(Melkbus bus : this.vollebussen) {
			omzet += bus.getCapaciteit()*melkprijs;
		}
		this.vollebussen=new ArrayList<Melkbus>();
		
	}
	
	int getOmzet() {
		return this.omzet;
	}


}


class Koe{
	private int melk;
	private int productieSnelheid;
	private int capaciteit;

	Koe(){
		this.melk=0;
		this.productieSnelheid=3;
		this.capaciteit=100;
	}

	Koe(int productiesnelheid){
		this();
		this.productieSnelheid=productiesnelheid;
	}

	void produceren(int uren) {
		melk+=productieSnelheid*uren;
		melk = Math.min(melk, capaciteit);
	}

	int getMelk() {
		return this.melk;
	}

	boolean melken(int liter) {
		if (liter<=this.melk) {
			this.melk-=liter;
			return true;
		} 
		return false;
	}
}



class Melkbus{
	private int capaciteit;
	private int inhoud;

	Melkbus(int capaciteit){
		this.capaciteit=capaciteit;
		this.inhoud=0;
	}

	int getInhoud() {
		return this.inhoud;
	}

	int getCapaciteit() {
		return this.capaciteit;
	}

	int getRuimte() {
		return this.capaciteit-this.inhoud;

	}

	void voegtoe(int liters) {
		this.inhoud+=liters;
	}

	boolean vol() {
		return this.capaciteit==this.inhoud;

	}
}


class Melkmachine {
	private Melkbus bus;

	void laadMelkbus(Melkbus bus) {
		this.bus = bus;
	}

	void Melken(Koe koe) {
		if(this.bus != null) {
			int liters = Math.min(this.bus.getRuimte(),koe.getMelk());
			if(koe.melken(liters)) {
				this.bus.voegtoe(liters);
			}
		} else {
			System.out.println("Geen melkbus geladen");

		}
	}

	boolean isDeBusVol() {
		return this.bus.vol();
	}


	Melkbus verwijderMelkbus() {
		Melkbus melkbus = this.bus;
		this.bus=null;
		return melkbus;

	}
}









