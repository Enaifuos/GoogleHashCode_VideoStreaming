package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.*;

public class EndPointsTest {

	/*	Attributs	*/
	EndPoint endPoint ;

	@Before
	public void init() {
		Map<Cache,Integer> delayCache = new HashMap<>();
		Map<Video,Integer> nbRequests = new HashMap<>();

		delayCache.put(new Cache(0,100), 100);
		delayCache.put(new Cache(1, 200),300);
		delayCache.put(new Cache(2, 100),200);

		nbRequests.put(new Video(1,50), 1);
		nbRequests.put(new Video(2,50), 1);
		nbRequests.put(new Video(3,50), 0);
		nbRequests.put(new Video(4,50), 0);
		nbRequests.put(new Video(5,60), 80);
		nbRequests.put(new Video(6,60), 40);
		nbRequests.put(new Video(7,30), 80);
		nbRequests.put(new Video(8,30), 40);

		this.endPoint = new EndPoint(1,1000,delayCache,nbRequests);
	}

	@Test
	public void testDelayCache() {

			assertTrue(this.endPoint.getDelayCache(2)==200);
			assertTrue(this.endPoint.getDelayCache(0)==100);
			assertFalse(this.endPoint.getDelayCache(0)==200);
			assertFalse(this.endPoint.getDelayCache(1)!=300);
			assertTrue(this.endPoint.getDelayCache(4)==-1);
	}

	@Test
	public void testGetNbRequests() {
		assertTrue(this.endPoint.getNbRequestVideo(2)==1);
		assertTrue(this.endPoint.getNbRequestVideo(10)==-1);
	}

	@Test
	public void testNearestCache() {
		assertTrue(this.endPoint.theNearestCache().getID()==0);
		assertFalse(this.endPoint.theNearestCache().getID()==1);
		assertFalse(this.endPoint.theNearestCache().getID()==2);
	}

	@Test
	public void testAddCache() {
		assertTrue(this.endPoint.getDelayCache(12)==-1);
		this.endPoint.addCache(new Cache(12,100), 700);
		assertTrue(this.endPoint.getDelayCache(12)==700);
	}

	@Test
	public void testGetMostOptimizedCacheInQueue() {
		try {
			Queue<Cache> queue = this.endPoint.getMostOptimizedCacheInQueue();
			assertEquals(queue.peek().getID(),0);
			queue.poll();
			assertEquals(queue.peek().getID(),2);
		} catch (NetworkObjectException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetMostOptimizedCacheToStoreAVideo() {
		try{
			Video video = new Video(0,90);
			assertEquals(this.endPoint.getMostOptimizedCache(video).getID(),0);
		}catch (NetworkObjectException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetMostOptimizedCacheToStoreAVideo2() {
		try{
			Video video = new Video(0,101);
			assertEquals(this.endPoint.getMostOptimizedCache(video).getID(),1);
		}catch (NetworkObjectException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetMostOptimizedCacheToStoreAVideoThrowingException() {
		try{
			Video video = new Video(0,201);
			assertEquals(this.endPoint.getMostOptimizedCache(video),null);
		}catch (NetworkObjectException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCalculRatio() {
		Video video = this.endPoint.getMostRequestedVideo();
		Integer nbRequest = this.endPoint.getNbRequestVideo(video.getId());
		assertTrue(this.endPoint.ratio(video) == ((double)(nbRequest)/video.getSize()));
	}

	@Test
	public void tetsCalculRatioVideoWithZeroSize() {
		Video video = new Video(10,0);
		Integer nbRequest = 20;
		assertTrue(this.endPoint.ratio(video) == 0);
	}

	@Test
	public void testGetBestVideoRatio() {
		Queue<Video> sortedVideos = this.endPoint.getBestVideosWithRatio();
		assertEquals(sortedVideos.peek().getId(),7);
		sortedVideos.poll();
		assertTrue(  ( sortedVideos.peek().getId() == 5) || ( sortedVideos.peek().getId() == 8 ) );
	}

	@Test
	public void testGetLessRatio() {
		Queue<Video> sortedVideos = this.endPoint.getBestVideosWithRatio();
		while ( sortedVideos.size() > 1 ) {
			sortedVideos.poll();
		}

		assertTrue(sortedVideos.peek().getId() == 3 || sortedVideos.peek().getId() == 4 );
	}
}

