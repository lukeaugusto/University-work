import java.util.Stack;


public class BackButtonBackend {
	
	private class URLandTime implements Comparable<URLandTime> {

		private String url;
		private double time;
		public String getUrl () {
			return url;
		}

		public double getTime () {
			return time;
		}

		public URLandTime(final String url_, final double time_) {
			url = url_;
			time = time_;
		}

		public String toString () {
			String result = "url: " + url +"  " + "Time: "+time;
			return result;
		}
		
		@Override
		public int compareTo (final URLandTime o) {
			if (this.time > o.getTime()) {
				return 1;
			} else if (this.time < o.getTime()) {
				return -1;
			}
			// Check if the urls are equal
			if (this.time == o.getTime() && this.url == o.getUrl()){ 
				return 2;
			}
			return 0;
		}

	}

	SuperStack<URLandTime> visited_past = new SuperStack<URLandTime>();
	Stack<URLandTime> visited_future = new Stack<URLandTime>();
	
	public final void add (final String url, final double time) {
		// Add information of the currently visited website.
		
		URLandTime element = new URLandTime(url,time);
		visited_past.push(element);
	}
	
	public final URLandTime back () {
		// Press back button.
		
		if(!visited_past.isEmpty()){
		
			URLandTime element;
	
			// Remove the first element in visited_past and put it in visited_future
			element = visited_past.pop();
			visited_future.push(element);
			
			return(element);
		}
		
		return(null);
	}
	
	public final URLandTime forward () {
		// Press forward. Move to the next url.

		if(!visited_future.isEmpty()){
			URLandTime element;
			
			// Remove the first element in visited_future and put it in visited_past
			element = visited_future.pop();
			visited_past.push(element);
			
			return(element);
		}
		
		return(null);
	}
	
	public final URLandTime backMostTimeSpent () {
		// Return an instance of URLandTime with the most time spent 
		// from the past history. Note, this function does not take into account
		// future history.
		

		if(!visited_past.isEmpty()){
			URLandTime i;
	    	
	    	i = visited_past.max();
			
	    	// Search for the url with the greater time, putting the elements above it in visited_past 
	    	while(i.compareTo(visited_past.peek()) != 2)
	    		visited_future.push(visited_past.pop());
	    	
	    	// Remove the url from the visited_past and put it in the visited_future
	    	visited_past.pop();
	    	visited_future.push(i);
	    	
	    	return(i);
		}
		
		return(null);
	}
	
	
	public final String toString () {
		StringBuilder sb = new StringBuilder();
		String result = "Past: " +"\n";
		sb.append(result);
		sb.append("[" +"\n");
		for (URLandTime urLandTime : this.visited_past.getMainStack()) {
			sb.append(urLandTime.toString()+" \n");
		}
		sb.append("]" +"\n");
		result = "Future: "  +"\n";
		sb.append(result);
		sb.append("[");
		for (URLandTime urLandTime : this.visited_future) {
			sb.append(urLandTime.toString()+" \n");
		}
		sb.append("]" +"\n");
		return new String(sb);
	}

}

