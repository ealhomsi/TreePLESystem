package ca.mcgill.ecse321.treeple.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.treeple.model.EnvironmentalScientist;
import ca.mcgill.ecse321.treeple.model.Location;
import ca.mcgill.ecse321.treeple.model.MunicipalArborist;
import ca.mcgill.ecse321.treeple.model.Municipality;
import ca.mcgill.ecse321.treeple.model.Resident;
import ca.mcgill.ecse321.treeple.model.Transaction;
import ca.mcgill.ecse321.treeple.model.Tree;
import ca.mcgill.ecse321.treeple.model.Tree.TreeSpecies;
import ca.mcgill.ecse321.treeple.model.Tree.TreeStatus;
import ca.mcgill.ecse321.treeple.model.TreePLESystem;
import ca.mcgill.ecse321.treeple.persistence.PersistenceXStream;

@Service
public class TreePLEService {
	private TreePLESystem rm;

	/** Constructor **/
	public TreePLEService(TreePLESystem rm) {
		this.rm = rm;
	}


	/** Helper Methods **/
	public boolean checkIfNullOrEmptyString(String s) {
		return s == null || s.trim().contentEquals("");
	}
	
	public String hashPassword(String password_plaintext, String salt) {
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		return(hashed_password);
	}
	
	public boolean checkPassword(String password_plaintext, String salt, String hashedPassword) {
		String hash = hashPassword(password_plaintext, salt);
		return hashedPassword.contentEquals(hash);
	}
	
	
	/** Business Logic **/
	public Tree CreateTree(TreeSpecies species, TreeStatus status, int diameter, double lon, double lat, Municipality m) throws InvalidInputException {
		if(m == null)
			throw new InvalidInputException("Municipality cannot be null");
	
		
		Location l = new Location(lon, lat);
		Tree t = new Tree(diameter, l, m);
		t.setSpecies(species);
		t.setStatus(status);
		rm.addTree(t);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return t;
	}
	
	public Resident findResidentByName(String name) throws InvalidInputException {
		for(Resident r: rm.getResidents())
			if(r.getName().contentEquals(name))
				return r;
		
		throw new InvalidInputException("Resident was not found");
	}
	
	public Tree findTreeById(int id) throws InvalidInputException {
		for(Tree t: rm.getTrees())
			if(t.getId() == id)
				return t;
		
		throw new InvalidInputException("Tree was not found");
	}
	public Municipality createMunicipality(String name) throws InvalidInputException {
		if(checkIfNullOrEmptyString(name))
			throw new InvalidInputException("Municipality Name cannot be empty or null");
		
		Municipality m = new Municipality(name);
		rm.addMunicipality(m);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return m;
	}
	
	public Resident CreateResident(String aName, String aEmail, String aPassword, double lon, double lat, String type) throws InvalidInputException {
		if(checkIfNullOrEmptyString(aName)|| checkIfNullOrEmptyString(aEmail) || checkIfNullOrEmptyString(aPassword))
			throw new InvalidInputException("Resident did not provide necessary information");
		
		String aSalt = BCrypt.gensalt();
		String aPasswordSalted = hashPassword(aPassword, aSalt);
		
		Location aPropertyLocation = new Location(lon, lat);
		
		//creating a resident
		Resident r = null;
		type = type.toLowerCase();
		
		if(type.contentEquals("resident"))
			r = new Resident(aName, aEmail, aSalt, aPasswordSalted, aPropertyLocation);
		else if(type.contentEquals("environmentalscientist"))
			r = new EnvironmentalScientist(aName, aEmail, aSalt, aPasswordSalted, aPropertyLocation);
		else if (type.contentEquals("municipalarborist"))
			r = new MunicipalArborist(aName, aEmail, aSalt, aPasswordSalted, aPropertyLocation);
		else
			throw new InvalidInputException("Type is invalid");
		
		rm.addResident(r);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return r;
	}
	
	public Transaction CreateTransaction(Time  aTime, Date aDate, Resident r, Tree t, Transaction.TreeStatus aChangedStatusTo) throws InvalidInputException {
		if(r == null || t == null)
			throw new InvalidInputException("Resident is null or Tree is null");
		
		Transaction temp = new Transaction(aTime, aDate, r, t);
		temp.setChangedStatusTo(aChangedStatusTo);
		
		//change the tree status
		Tree.TreeStatus newStatus = Tree.TreeStatus.values()[aChangedStatusTo.ordinal()];
		t.setStatus(newStatus);
		
		//add transaction
		rm.addTransaction(temp);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return temp;
	}
	
	public List<Tree> findAllTrees() {
		return rm.getTrees();
	}

	public List<Municipality> findAllMunicipalities() {
		return rm.getMunicipalities();
	}
	
	public List<Resident> findAllResidents() {
		return rm.getResidents();
	}
	
	public List<Transaction> findAllTransactions() {
		return rm.getTransactions();
	}
	
//	public Participant createParticipant(String name) throws InvalidInputException {
//		if (checkIfEmptyOrNull(name))
//			throw new InvalidInputException("Participant name cannot be empty!");
//		//check if participant name already exists
//		for(Participant tmp: rm.getParticipants()) {
//			if(tmp.getName().equals(name))
//				throw new InvalidInputException("Participant name already exists");
//		}
//		
//		Participant p = new Participant(name);
//		
//		rm.addParticipant(p);
//		PersistenceXStream.saveToXMLwithXStream(rm);
//		return p;
//	}
//
//	public Event createEvent(String name, Date date, Time startTime, Time endTime) throws InvalidInputException {
//		if(name == null || date == null || startTime == null || endTime == null) 
//			throw new InvalidInputException("Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!");
//		else if (name.trim().contentEquals(""))
//			throw new InvalidInputException("Event name cannot be empty!");				
//		else if (startTime.compareTo(endTime) > 0)
//			throw new InvalidInputException("Event end time cannot be before event start time!");
//		
//		//check if event already exists
//		for(Event tmp: rm.getEvents()) {
//			if(tmp.getName().contentEquals(name))
//				throw new InvalidInputException("Event name already exists");
//		}
//		
//		Event e = new Event(name, date, startTime, endTime);
//		rm.addEvent(e);
//		PersistenceXStream.saveToXMLwithXStream(rm);
//
//		return e;
//	}
//
//	public Registration register(Participant p, Event e) throws InvalidInputException {
//		if (p == null || e == null)
//			throw new InvalidInputException("Participant needs to be selected for registration! Event needs to be selected for registration!");
//		else if(!checkIfParticipantExists(p.getName()) || !checkIfEventExists(e.getName())) {
//			throw new InvalidInputException("Participant does not exist! Event does not exist!");			
//		}
//		
//		//check if p has already registered for e
//		for(Event tmp: getEventsForParticipant(p)) {
//			if(tmp.getName().contentEquals(e.getName()))
//				throw new InvalidInputException("Participant " + p.getName() + " has already registered for " + e.getName());
//		}
//		
//		Registration r = new Registration(p, e);
//		rm.addRegistration(r);
//		PersistenceXStream.saveToXMLwithXStream(rm);
//
//		return r;
//	}
//	
//	private boolean checkIfParticipantExists(String name) {
//		for(Participant p: rm.getParticipants())
//			if(p.getName().contentEquals(name))
//				return true;
//		return false;
//	}
//	
//	private boolean checkIfEventExists(String name) {
//		for(Event e: rm.getEvents())
//			if(e.getName().contentEquals(name))
//				return true;
//		return false;
//	}
//	
//	public List<Event> findAllEvents() {
//		return rm.getEvents();
//	}
//
//	public List<Participant> findAllParticipants() {
//		return rm.getParticipants();
//	}
//
//	public List<Event> getEventsForParticipant(Participant p) {
//		List<Event> events = new ArrayList<>();
//		for (Registration r : rm.getRegistrations()) {
//			if (r.getParticipant().getName().contentEquals(p.getName()))
//				events.add(r.getEvent());
//		}
//
//		return events;
//	}
//
//	public Participant findParticipant(String name) throws InvalidInputException {
//		Participant p = null;
//		for(Participant tmp: rm.getParticipants()) {
//			if(tmp.getName().contentEquals(name)) {
//				p = tmp;
//				break;
//			}
//		}
//		
//		if (p == null)
//			throw new InvalidInputException("Participant was not found");
//		return p;
//	}
//
//	public Event findEvent(String name) throws InvalidInputException {
//		Event e = null;
//		for(Event tmp: rm.getEvents()) {
//			if(tmp.getName().contentEquals(name)) {
//				e = tmp;
//				break;
//			}
//		}
//		
//		if (e == null)
//			throw new InvalidInputException("Event was not found");
//		return e;
//	}

}