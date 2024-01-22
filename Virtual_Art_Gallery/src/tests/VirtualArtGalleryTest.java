package tests;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;


import dao.VirtualArtGalleryImpl;
import entity.Artwork;

public class VirtualArtGalleryTest {
	
	
	 public VirtualArtGalleryImpl artGallery;

	   
	
	@Test
	public void testUploadNewArtwork() {
		VirtualArtGalleryImpl artGallery = new VirtualArtGalleryImpl();
		Artwork testartwork = new Artwork(8,"Relegious","Painting of Radha Krishna",java.sql.Date.valueOf("2023-06-07"), "Clay","Url.jpg",3);
		boolean artworkAdded = artGallery.addArtwork(testartwork);
		assertTrue("New Artwork Uploaded Successfully",artworkAdded);
	}
	
	 @Test
	    public void testUpdateArtworkDetails() {
	        VirtualArtGalleryImpl gallery = new VirtualArtGalleryImpl();
	        Artwork existingArtwork = new Artwork(3,"City Lights", "City Life at night",java.sql.Date.valueOf ("2015-09-15"), "Photography", "citylights.jpg",5);
	        boolean isArtworkUpdated = gallery.updateArtwork(existingArtwork);
	        assertTrue("Artwork Updated Successfully", isArtworkUpdated);
	    }
	 
	 

}
