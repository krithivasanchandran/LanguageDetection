package snippet;

import redis.clients.jedis.Jedis;

public class Snippet {
	
	public static void main(String args[]){
		
		Jedis jedis = new Jedis("localhost"); 
		
	      System.out.println("Connection to server sucessfully"); 
	      System.out.println("Server is running: "+jedis.ping()); 
	      
	      jedis.rpush("test", "hello this is redis server");
	      
	      find(jedis);
	   } 
	
	private static void find(Jedis jedis){
		String server = jedis.get("test");
		
		System.out.println(" Output from the redis server ::: ::: :: " + server);
	}
}

