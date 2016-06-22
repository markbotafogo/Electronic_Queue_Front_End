package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;
import java.util.Collection;

import ufrj.sps.electronicqueueclient.Queue.Ticket;

import android.app.Application;
import android.content.res.Configuration;

public class Cache extends Application{
	
	private ArrayList<Queue> memory;
	private ArrayList<Ticket> myTickets;
	private static Cache singleton;
	private boolean running = false;
	private boolean userRegistered = false;
	private String userName;
	
    final static int NONE = 0;
	final static int OWNER = 1;
	final static int CONTROLLER = 2;
	
	private int user = NONE;
	private int userID = -1;
	
	public void onCreate(){
		
		super.onCreate();
		singleton = this;
		memory = new ArrayList<Queue>();
		myTickets = new ArrayList<Ticket>();
		
	}
	
	Cache getInstance(){
		
		return singleton;
		
	}
	
	boolean add(Queue queue){
		
		return memory.add(queue);
		
	}
	
	void addAll(ArrayList<Queue> queues){
		
		for(int i = 0; i < queues.size(); i++ ){
			
			Queue temp = this.searchById(queues.get(i));
			
			if (temp == null)
				memory.add(queues.get(i));		
			
		}
		
	}
	
	Queue searchById(Queue queue){
		
		for(int i = 0; i < memory.size(); i++ ){
		
			if(queue.getID() == memory.get(i).getID())
				return memory.get(i);
		
		}
		
		return null;
		
	}
	
	Queue searchById(int id){
		
		for(int i = 0; i < memory.size(); i++ ){
		
			if(id == memory.get(i).getID())
				return memory.get(i);
		
		}
		
		return null;
		
	}
	
	ArrayList<Queue> searchByName(Queue queue){
		
		ArrayList<Queue> temp = new ArrayList<Queue>();
		
		for(int i = 0; i < memory.size(); i++ ){
		
			if(queue.getName().equals(memory.get(i).getName()))
				temp.add(memory.get(i));
		
		}
		
		if (temp.isEmpty())
			return null;
		
		return temp;
	}
	
	ArrayList<Queue> getAll(){
		
		return memory;
		
	}
	
	boolean isEmpty(){
		
		return memory.isEmpty();
		
	}
	
	boolean addTicket(Ticket ticket){
		
		return myTickets.add(ticket);
		
	}
	
	ArrayList<Ticket> getAllTickets(){
		
		return myTickets;
		
	}

	public boolean isRunning() {
		
		return running;
		
	}

	public void setRunning(boolean running) {
		
		this.running = running;
		
	}

	public boolean isUserRegistered() {
		
		return userRegistered;
		
	}

	public void setUserRegistered(boolean userRegistered) {
		
		this.userRegistered = userRegistered;
		
	}

	public int getUser() {
		
		return user;
		
	}

	public void setUser(int user) {
		
		this.user = user;
		
	}

	public int getUserID() {
		
		return userID;
		
	}

	public void setUserID(int userID) {
		
		this.userID = userID;
		
	}

	public String getUserName() {
		
		return userName;
		
	}

	public void setUserName(String userName) {
		
		this.userName = userName;
		
	}
	
	public void logout(){
		
		this.user = Cache.NONE;
		this.userID = -1;
		this.userRegistered = false;
		this.userName = null;
		
	}
	
}
