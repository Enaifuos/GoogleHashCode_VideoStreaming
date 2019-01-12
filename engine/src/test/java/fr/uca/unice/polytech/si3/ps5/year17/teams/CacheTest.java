package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheTest {
    /*	Attributs	*/
    Cache cache;
    int id = 666;
    int totalsize = 50;


    @Before
    public void init() {
        this.cache = new Cache(id,totalsize);
    }

    @Test
    public void testAddVideoWithEnoughPlace() throws NotEnoughPlaceException {
        int idVideo = 1;
        int sizeVideo = 30;
        Video video = new Video(idVideo,sizeVideo);

        assertEquals(this.cache.getSizeLeft(),50);

        this.cache.addVideo(video);
        assertEquals(this.cache.getSizeLeft(),50-30);
        // Video has been added , cache's size has been reduced by video's size
    }

    @Test
    public void testAddVideoWithoutEnoughPlace() {
        int idVideo1 = 1;
        int idVideo2 = 2;

        int sizeVideo1 = 30;
        int sizeVideo2 = 40;

        Video video1 = new Video(idVideo1,sizeVideo1);
        Video video2 = new Video(idVideo2,sizeVideo2);

        try{
            this.cache.addVideo(video1);
        }catch(NotEnoughPlaceException e) {
            assertEquals(false,true);
        }

        try{
            this.cache.addVideo(video2);
            assertEquals(false,true);
        }catch (NotEnoughPlaceException e) {
            assertEquals(true,true);
        }

        // The NotEnoughPlaceException hasn't been raised for the new add, well !
        // but it's raised for the second add because there isn't enough place
    }

    @Test
    public void testContainsVideo() throws NotEnoughPlaceException {
        int idVideo = 2;
        int sizeVideo = 30;
        Video video = new Video(idVideo,sizeVideo);
        this.cache.addVideo(video);

        assertTrue(this.cache.containsVideo(video));
        // Cache's videos list contains the added video
    }

    @Test
    public void testGetSizeLeft() {
        assertEquals(this.cache.getSizeLeft(),this.totalsize);
        // Without adding any video to the cache, it size doesn't change
    }

}
