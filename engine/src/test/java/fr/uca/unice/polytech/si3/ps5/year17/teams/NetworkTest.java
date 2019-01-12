package fr.uca.unice.polytech.si3.ps5.year17.teams;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.*;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import org.junit.Before;
import org.junit.Test;

public class NetworkTest {

	/*	Attributs	*/
	private Network network;
	private ArrayList<Cache> caches;
	private ArrayList<Video> dataCenter;
	private ArrayList<EndPoint> endPoints;

	@Before
	public void init() {

		/*	List des videos	*/
		Video video1,video2,video3;
		int idVideo1 = 0, idVideo2 =1, idVideo3 = 2;
		int sizeVideo1 = 20, sizeVideo2 = 30, sizeVideo3 = 50;

		video1 = new Video(idVideo1,sizeVideo1);
		video2 = new Video(idVideo2,sizeVideo2);
		video3 = new Video(idVideo3,sizeVideo3);

		/*	Liste des caches	*/
		Cache cache1, cache2, cache3;
		int sizeCache1 = 40, sizeCache2 = 40, sizeCache3 = 70;
		int idCache1 = 0, idCache2 = 1, idCache3 = 2;

		cache1 = new Cache(idCache1,sizeCache1);
		cache2 = new Cache(idCache2,sizeCache2);
		cache3 = new Cache(idCache2,sizeCache3);

		this.caches = new ArrayList<>();
		this.caches.add(cache1);
		this.caches.add(cache2);
		this.caches.add(cache3);


		/*	Liste des endPoints	*/
		Map<Cache,Integer> delayCache = new HashMap<>();
		Map<Cache,Integer> delayCache2 = new HashMap<>();
		Map<Video,Integer> nbRequests = new HashMap<>();
		Map<Video,Integer> nbRequests2 = new HashMap<>();

		delayCache.put(new Cache(0,100), 100);
		delayCache.put(new Cache(1,500), 300);
		delayCache.put(new Cache(2,400), 200);

		nbRequests.put(new Video(1,50), 1);
		nbRequests.put(new Video(2,40), 1);
		nbRequests.put(new Video(3,500), 0);
		nbRequests.put(new Video(4,800), 0);

		delayCache2.put(new Cache(0,100), 300);
		delayCache2.put(new Cache(1,100), 200);
		delayCache2.put(new Cache(2,100), 100);

		nbRequests2.put(new Video(1,500), 1);
		nbRequests2.put(new Video(2,48115), 1);
		nbRequests2.put(new Video(3,505), 0);
		nbRequests2.put(new Video(4,4), 0);

		EndPoint endPoint0 = new EndPoint(1,1000,delayCache,nbRequests);
		EndPoint endPoint1 = new EndPoint(2,500,delayCache2,nbRequests2);

		this.endPoints = new ArrayList<>();
		endPoints.add(endPoint0);
		endPoints.add(endPoint1);


		/*	Liste des videos dans le dataCenter	*/
		this.dataCenter = new ArrayList<>();
		dataCenter.add(video1);
		dataCenter.add(video2);
		dataCenter.add(video3);

		/*	La network	*/
		this.network = new Network(dataCenter,endPoints,caches);
	}


	@Test
	public void testGetters() {

		assertEquals(this.network.getCaches(),this.caches);
		assertEquals(this.network.getDatacenter(),this.dataCenter);
		assertEquals(this.network.getEndpoints(),this.endPoints);
	}


	@Test
	public void testSetCaches() {


		Cache cacheA, cacheB;
		int sizeCache1 = 50, sizeCache2 = 50;
		int idCache1 = 4, idCache2 = 5;

		cacheA = new Cache(idCache1,sizeCache1);
		cacheB = new Cache(idCache2,sizeCache2);

		ArrayList<Cache> lesCaches = new ArrayList<>();
		lesCaches.add(cacheA);
		lesCaches.add(cacheB);

		this.network.setCaches(lesCaches);

		assertNotEquals(this.network.getCaches(),this.caches);
		assertEquals(this.network.getCaches(),lesCaches);

	}

	@Test
	public void tetSetDataCenter() {

		int idVideo1 = 1, idVideo2 =2;
		int sizeVideo1 = 20, sizeVideo2 = 30;

		Video video1 = new Video(idVideo1,sizeVideo1);
		Video video2 = new Video(idVideo2,sizeVideo2);

		ArrayList<Video> ThedataCenter = new ArrayList<>();
		ThedataCenter.add(video1);
		ThedataCenter.add(video2);

		this.network.setDatacenter(ThedataCenter);

		assertNotEquals(this.network.getDatacenter(),this.dataCenter);
		assertEquals(this.network.getDatacenter(),ThedataCenter);
	}

	@Test
	public void testSetEndPoints() {
		ArrayList<EndPoint> TheendPoints = null;
		this.network.setEndpoints(TheendPoints);

		assertNotEquals(this.network.getEndpoints(),this.endPoints);
		assertEquals(this.network.getEndpoints(),TheendPoints);
	}

	@Test
	public void testAddVideoToCacheSucceed() {

		/*	Création de la video a ajouter	*/
		int newVideoId = 3;
		int newVideoSize = 50;
		Video newVideo = new Video(newVideoId,newVideoSize);

		/*	Ajouter la video au dataCenter	*/
		this.dataCenter.add(newVideo);

		assertEquals(this.dataCenter.get(newVideoId),newVideo);

		try {
			this.network.addVideoToCache(newVideoId, 2);
			assertEquals(true,true);
		} catch (NotEnoughPlaceException e) {
			// TODO Auto-generated catch block
			assertEquals(false,true);
		}

		// The third cache server ( with id = 2 and size = 70 ) can store the new
		// video ( with id size = 50 )

	}

	@Test
	public void testAddVideoToCacheFail() {

		/*	Creation de la video a ajouter	*/
		int newVideoId = 3;
		int newVideoSize = 50;
		Video newVideo = new Video(newVideoId,newVideoSize);

		/*	Ajouter la video au dataCenter	*/
		this.dataCenter.add(newVideo);

		assertEquals(this.dataCenter.get(newVideoId),newVideo);

		try {
			this.network.addVideoToCache(newVideoId, 1);
			assertEquals(true,false);
		} catch (NotEnoughPlaceException e) {
			// TODO Auto-generated catch block
			assertEquals(true,true);
		}

		// The second cache server ( with id = 1 , size = 40 ) , can't store the new
		// video ( with size = 50 )
		// because there is no enough place
	}

	@Test
    public void testVideoCut(){
	    Video lourdevideo = new Video(84,5959);
	    this.dataCenter.add(lourdevideo);
	    this.dataCenter.add(new Video(99,125));
	    assertEquals(network.videosbanned(80).size(),4);
	    assertEquals(network.videosbanned(80).contains(lourdevideo),false);
	    assertEquals(network.videosbanned(25).size(),1);
        assertEquals(network.videosbanned(19).size(),0);
        assertEquals(network.videosbanned(0).size(),0);
        assertEquals(network.videosbanned(100).size(),5);
        assertEquals(network.videosbanned(99).size(),4);
    }


}
