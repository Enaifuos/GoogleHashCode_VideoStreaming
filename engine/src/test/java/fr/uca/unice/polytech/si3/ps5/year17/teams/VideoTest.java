package fr.uca.unice.polytech.si3.ps5.year17.teams;

import org.junit.Test;
import static org.junit.Assert.*;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.*;


public class VideoTest {

	@Test
	public void testVideo() {
		int id = 1;
		int size = 30;
		Video video = new Video(id,size);

		assertEquals(video.getSize(),size);
		assertEquals(video.getId(),id);
	}
}
