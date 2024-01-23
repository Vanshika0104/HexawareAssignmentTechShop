package tests;

import static org.junit.Assert.*;



import org.junit.Test;


import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import entity.Gallery;

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
	 @Test 
	 public void testRemoveArtwork() {
		 VirtualArtGalleryImpl artwork = new VirtualArtGalleryImpl();
	     int  artworkToRemove = 5;
	     boolean isArtworkRemoved = artwork.removeArtwork(artworkToRemove);
	     assertTrue("Artwork removed successfully", isArtworkRemoved);
	 }
	 
	 @Test
	 public void testsearchArtwork() {
		 VirtualArtGalleryImpl artwork = new VirtualArtGalleryImpl(); 
		 String keywordToSearch = "inspiration";
		 artwork.searchArtworks(keywordToSearch);
		 assertEquals(1, artwork.searchArtworks("inspiration").size());
	 }
	 @Test
		public void testCreateNewGallery() {
			VirtualArtGalleryImpl Gallery = new VirtualArtGalleryImpl();
			Gallery testgallery = new Gallery(8,"memories","photographs","Ahmedabad",4, "10:00AM - 6:00PM");
			boolean GalleryAdded = Gallery.createNewGallery(testgallery);
			assertTrue("New Gallery Created Successfully",GalleryAdded);
		}
	
	 @Test
	    public void testUpdateGalleryDetails() {
	        VirtualArtGalleryImpl gallery = new VirtualArtGalleryImpl();
	        Gallery update = new Gallery(8,"City Lights", "City Life at night", "Germany", 6,"10:00AM- 6:00PM");
	        boolean isGalleryUpdated = gallery.updateGallery(update);
	        assertTrue("Gallery Updated Successfully", isGalleryUpdated);
	    }
	 @Test 
	 public void testRemoveGallery() {
		 VirtualArtGalleryImpl gallery = new VirtualArtGalleryImpl();
	     int  galleryToRemove = 5;
	     boolean isGalleryRemoved = gallery.removeArtwork(galleryToRemove);
	     assertTrue("Gallery removed successfully", isGalleryRemoved);
	 }
	 @Test
	 public void testsearchGallery() {
		 VirtualArtGalleryImpl gallery = new VirtualArtGalleryImpl(); 
		 String keywordToSearch = "memories";
		 gallery.searchArtworks(keywordToSearch);
		 assertEquals(1, gallery.searchArtworks("memories").size());
	 }

	
	 
	 
	 
	
	 
	 

}
