package snippet;

public class Snippet {
	<ehcache:expiry>
	      <!--
	        time to idle, the maximum time for an entry to remain untouched
	          Entries to the Cache can be made to expire after a given time
	          other options are:
	             * <ttl>, time to live;
	             * <class>, for a custom Expiry implementation; or
	             * <none>, for no expiry
	      -->
	      <ehcache:tti unit="minutes">2</ehcache:tti>
	    </ehcache:expiry>
}

