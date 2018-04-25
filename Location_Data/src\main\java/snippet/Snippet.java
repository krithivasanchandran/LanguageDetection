package snippet;

import redis.clients.jedis.Jedis;

public class Snippet {
	
	public static void main(String args[]){
		
		Jedis jedis = new Jedis("localhost"); 
		
	      System.out.println("Connection to server sucessfully"); 
	      System.out.println("Server is running: "+jedis.ping()); 
	      
	   //   jedis.rpush("test", "hello this is redis server");
//	     jedis.lpush("test123", "america is a great country ");
//	     jedis.lpush("test123", "india is a great country ");
//	     jedis.lpush("test123", "britian is a shit country ");

	      
	      find(jedis);
	   } 
	
	private static void find(Jedis jedis){
		String server = jedis.rpop("avisrental");
		
		System.out.println(" Output from the redis server ::: ::: :: " + server);
		

//		String server1 = jedis.rpop("test123");
//		
//		System.out.println(" Output from the redis server ::: ::: :: " + server1);
	}
}

