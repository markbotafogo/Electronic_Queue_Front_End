package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

public class Queue implements Comparable<Queue>{
	
	private String name;
	private int id;
	private int actualNumber;
	private int nextPassword;
	private Ticket ticket;
	private boolean follow = false;
	
    private final String urlEnter = "http://" + TheIP + Port + "/axis2/services/EQCloud/enterQueue?queue=";
    private final String urlConsume = "http://" + TheIP + Port + "/axis2/services/EQCloud/consumeQueue?queue=";
	private final String urlUpdate = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueId?search=";
	
	static final int NUMBER = 2;
	static final int PASSWORD = 3;
	
	public class Ticket implements Comparable<Ticket>{
		
		private String queue;
		private int password;
		private int id;
		private Calendar created; 
		
		Ticket(String queue, int number, int id){
			
			this.queue = queue;
			this.password = number;
			this.id = id;
			this.created = Calendar.getInstance();		
			
		}
		
		String getName(){
			
			return this.queue;
			
		}
		
		int getPassword(){
			
			return this.password;
			
		}
		
		int getID(){
			
			return this.id;
			
		}
		
		Date getTimeOfCreation(){
			
			return created.getTime();
			
		}

		@Override
		public int compareTo(Ticket other) {
			// TODO Auto-generated method stub
			return this.queue.compareTo(other.queue);
		}
		
	}
	
	Queue(int id, String name, int actualNumber, int nextPassword){
		
		this.id = id;
		this.actualNumber = actualNumber;
		this.name = name;
		this.nextPassword = nextPassword;
		
	}
	
	
	Ticket createTicket(){
		
		if (ticket != null)
			return null;
		
		HttpRequester httpRequester = new HttpRequester();
		httpRequester.setRequest(urlEnter + this.id);
		String answer = httpRequester.getResponse();
		
		ticket = new Ticket(this.name, Integer.parseInt(answer), this.id);
		
		return ticket;
		
	}
	
	Ticket getTicket(){
		
		return this.ticket;
		
	}
	
	void removeTicket(){
		
		this.ticket = null;
		
	}
	
	void update(){
		
		HttpRequester httpRequester = new HttpRequester();
		httpRequester.setRequest(urlUpdate + this.id);
		ArrayList<String> answer = httpRequester.getResponses();
		String[] temp = answer.get(0).split(":"); 
		
		this.actualNumber = Integer.parseInt(temp[NUMBER]);	
		this.nextPassword = Integer.parseInt(temp[PASSWORD]);
		
	}
	
	boolean consume(){
			
			HttpRequester httpRequester = new HttpRequester();
			httpRequester.setRequest(urlConsume + this.id);
			String answer = httpRequester.getResponse();
			
			if (answer.equals("true"))
				return true;
			else
				return false;
							
	}
	
	String getName(){
		
		return this.name;
		
	}
	
	int getID(){
		
		return this.id;
		
	}
	
	int getNumber(){
		
		return this.actualNumber;
		
	}
	
	int getNumberTicket(){
		
		return this.ticket.getPassword();		
		
	}
	
	public int getNextPassword() {
		
		return nextPassword;
	}

	void follow(boolean follow){
		
		this.follow = follow;
		
	}

	boolean isFollowing(){
		
		return this.follow;
		
	}

	@Override
	public int compareTo(Queue other) {
		// TODO Auto-generated method stub
		
		return this.name.compareTo(other.name);
			
	}

}
