package main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.SQLException;
import exception.ArtworkNotFoundException;
import exception.UserNotFoundException;

import java.util.List;
import java.util.Properties;

import dao.IVirtualArtGallery;
import dao.VirtualArtGalleryImpl;

import entity.Artwork;
import entity.Gallery;
import util.DBConnUtil;
import util.DBPropertyUtil;
import util.DatabaseConnection;
import java.util.Scanner;
public class Main {
	
	public static void main(String[] args)  {
		Properties properties = new Properties();
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream("util/config.properties")) {
		    if (input != null) {
		        properties.load(input);
		    } else {
		        System.out.println("config.properties not found");
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter 1 for Add Artwork:");
		System.out.println("Enter 2 for Update Artwork:");
		System.out.println("Enter 3 for Remove Artwork:");
		System.out.println("Enter 4 for GetArtworkByID :");
		System.out.println("Enter 5 for SearchArtwork :");
		System.out.println("Enter 6 for AddArtwork to Favourite :");
		System.out.println("Enter 7 for GetArtwork From Favourite:");
		System.out.println("Enter 8 for RemoveArtwork from Favourite:");
		System.out.println("Enter 9 for Creating New Gallery:");
		System.out.println("Enter 10 for Updating Gallery:");
		System.out.println("Enter 11 for Removing Gallery:");
		System.out.println("Enter 12 for Searching Gallery:");
		System.out.println("Enter 13 for Exit:");
		
		
		
		
        int choice = sc.nextInt();
        
		// Instantiate DatabaseConnection
        DatabaseConnection connector = new DatabaseConnection();
        String connectionStrings = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            // Open a database connection
            Connection connection = connector.openConnection();
            System.out.println("Connected to the Database");
            IVirtualArtGallery artworks = new VirtualArtGalleryImpl(connection);
            switch(choice) {
            
            case 1:
            	
                Artwork insertartwork = new Artwork();
                
                    System.out.println("Enter artwork title:");
                    String title = sc.next();

                    System.out.println("Enter artwork description:");
                    String description = sc.next();

                    System.out.println("Enter artwork medium:");
                    
                    String medium = sc.next();

                    System.out.println("Enter artwork image URL:");
                    String imageURL = sc.next();
                    
                    System.out.println("Enter ArtistId:");
                    int ArtistId =sc.nextInt();

                    insertartwork.setTitle(title);
                    insertartwork.setDescription(description);
                    insertartwork.setMedium(medium);
                    insertartwork.setImageURL(imageURL);
                    insertartwork.setArtistID(ArtistId);

                    // Call the addArtwork method
                    boolean artworkAdded = artworks.addArtwork(insertartwork);

                    if (artworkAdded) {
                        System.out.println("Artwork added successfully!");
                    } else {
                        System.out.println("Failed to add artwork.");
                    }
                   break;
            case 2:
            	//Perform Update Artwork
            	 Artwork updatedArtwork = new Artwork();
            	 System.out.println("Enter artwork Id:");
                 int artworkId = sc.nextInt();
                 updatedArtwork.setArtworkID(artworkId); 
                 
                 System.out.println("Enter artwork title:");
                 String Title = sc.next();
                 updatedArtwork.setTitle(Title);
                 
                 System.out.println("Enter artwork Description:");
                 String Description = sc.next();
                 
                 updatedArtwork.setDescription(Description);
                 
                 System.out.println("Enter artwork Medium:");
                 
                 String Medium = sc.next();
                 updatedArtwork.setMedium(Medium);
                 
                 System.out.println("Enter artwork ImageURL:");
                 String ImageUrl = sc.next();
                 updatedArtwork.setImageURL(ImageUrl);
                 
                 System.out.println("Enter artistID:");
                 int artistID = sc.nextInt();
                 sc.nextLine();
                 updatedArtwork.setArtistID(artistID);
                 
                 boolean isArtworkUpdated = artworks.updateArtwork(updatedArtwork);

                 // Check the result
                 if (isArtworkUpdated) {
                     System.out.println("Artwork updated successfully!");
                 } else {
                     System.out.println("Failed to update artwork.");
                 }
                
                break;
            case 3:
                // Perform Remove Artwork
            	System.out.println("Enter the ArtworkId which you want to remove:");
            	int artworkIDToRemove = sc.nextInt();

                boolean isArtworkRemoved = artworks.removeArtwork(artworkIDToRemove);

                // Check the result
                if (isArtworkRemoved) {
                    System.out.println("Artwork removed successfully!");
                } else {
                    System.out.println("Failed to remove artwork.");
                }
               
                break;
                
            case 4:
            	try {
            	System.out.print("Enter the ArtworkId which you want:");
            	int artworkIdToRetrieve = sc.nextInt(); 
                Artwork retrievedArtwork = artworks.getArtworkById(artworkIdToRetrieve);
                if (retrievedArtwork != null) {
                    
                    System.out.println("Artwork ID: " + retrievedArtwork.getArtworkID());
                    System.out.println("Title: " + retrievedArtwork.getTitle());
                    System.out.println("Description: " + retrievedArtwork.getDescription());
                    System.out.println("CreationDate " + retrievedArtwork.getCreationDate());
                    System.out.println("Medium: " + retrievedArtwork.getMedium());
                    System.out.println("ImageURL: " + retrievedArtwork.getImageURL());
                    System.out.println("ArtistID: " + retrievedArtwork.getArtistID());
                    
                    
               } else {
                    System.out.println("Artwork not found or an error occurred.");
                }
            	}catch(ArtworkNotFoundException e) {
            		System.out.println("Artwork not found: " + e.getMessage());
            	}
                
                break;
            
            case 5:
            	System.out.println("Enter the Keyword to search:");
            	String keywordToSearch = sc.next(); 
                List<Artwork> matchingArtworks = artworks.searchArtworks(keywordToSearch);

                // Display the matching artwork
                if (!matchingArtworks.isEmpty()) {
                    System.out.println("Matching Artworks:");
                    for (Artwork artwork : matchingArtworks) {
                        System.out.println("Artwork ID: " + artwork.getArtworkID());
                        System.out.println("Title: " + artwork.getTitle());
                        System.out.println("Description: " + artwork.getDescription());
                        System.out.println("CreationDate: " + artwork.getCreationDate());
                        System.out.println("Medium: " + artwork.getMedium());
                        System.out.println("ImageURL: " + artwork.getImageURL());
                        System.out.println("ArtistID: " + artwork.getArtistID());
                        
                    }
                } else {
                    System.out.println("No matching artworks found.");
                }

                
                break;
            case 6:
                
                System.out.println("Enter UserId  And ArtworkId");
                int userID = sc.nextInt(); 
                int artworkID = sc.nextInt(); 

                boolean isAddedToFavorite = artworks.addArtworkToFavorite(userID, artworkID);

                if (isAddedToFavorite) {
                    System.out.println("Artwork added to favorites successfully!");
                } else {
                    System.out.println("Failed to add artwork to favorites.");
                }
                break;
            case 7:
                try {
                System.out.println("Enter UserID:");
                
                int userId = sc.nextInt();  
                List<Artwork> favoriteArtworks = artworks.getUserFavoriteArtworks(userId);

                
                System.out.println("User's favorite artworks:");
                for (Artwork artwork : favoriteArtworks) {
                    System.out.println(artwork);
                }
                }catch(UserNotFoundException e) {
            		System.out.println("User not found: " + e.getMessage());
            	}
                break;
            case 8: 
            	System.out.println("Enter userId:");
            	int usersID = sc.nextInt();
            	System.out.println("Enter ArtworkId:");
                int artworksID = sc.nextInt();

                // Call removeArtworkFromFavorite method
                boolean removed = artworks.removeArtworkFromFavorite(usersID, artworksID);

                if (removed) {
                    System.out.println("Artwork removed from favorites successfully!");
                } else {
                    System.out.println("Failed to remove artwork from favorites.");
                }
                break;
            case 9:
            	 Gallery createGallery = new Gallery();
            	 System.out.println("Enter Name of the Gallery:");
            	 String Name = sc.next();
            	 System.out.println("Enter Description of the Gallery:");
            	 String Descriptions = sc.next();
            	 System.out.println("Enter Location of the Gallery:");
            	 String Location = sc.next();
            	 System.out.println("Enter Curator(ArtistId):");
            	 int Curator = sc.nextInt();
            	 System.out.println("Enter OpeningHours of the Gallery:");
            	 String OpeningHours = sc.next();
            	 createGallery.setName(Name);
            	 createGallery.setDescription(Descriptions);
            	 createGallery.setLocation(Location);
            	 createGallery.setCurator(Curator);
            	 createGallery.setOpeningHours(OpeningHours);
            	 boolean galleryAdded = artworks.createNewGallery(createGallery);

                 if (galleryAdded) {
                     System.out.println("Gallery added successfully!");
                 } else {
                     System.out.println("Failed to add Gallery.");
                 }
                 break;
            case 10:
             Gallery updatedGallery = new Gallery(); 
             System.out.println("Enter Name of the Gallery:");
           	 String name = sc.next();
           	 updatedGallery.setName(name);
           	 System.out.println("Enter Description of the Gallery:");
           	 String descriptions = sc.next();
           	 updatedGallery.setDescription(descriptions);
           	 System.out.println("Enter Location of the Gallery:");
           	 String location = sc.next();
           	 updatedGallery.setLocation(location);
           	 System.out.println("Enter Curator(ArtistId):");
           	 int curator = sc.nextInt();
           	 updatedGallery.setCurator(curator);
           	 System.out.println("Enter OpeningHours of the Gallery:");
           	 String openingHours = sc.next();
           	 updatedGallery.setOpeningHours(openingHours);
           	 System.out.println("Enter GalleryId:");
          	 int galleryId = sc.nextInt();
          	 updatedGallery.setGalleryID(galleryId);
          	boolean isGalleryUpdated = artworks.updateGallery(updatedGallery);

            // Check the result
            if (isGalleryUpdated) {
                System.out.println("Artwork updated successfully!");
            } else {
                System.out.println("Failed to update artwork.");
            }
           	 break;
           	 case 11:
           		System.out.println("Enter the GalleryId which you want to remove:");
            	int galleryIDToRemove = sc.nextInt();

                boolean isGalleryRemoved = artworks.removeGallery(galleryIDToRemove);

                // Check the result
                if (isGalleryRemoved) {
                    System.out.println("Gallery removed successfully!");
                } else {
                    System.out.println("Failed to remove artwork.");
                }
               
                break;
           	 case 12:
           		System.out.println("Enter the Keyword to search:");
            	String keywordYouSearch = sc.next(); 
                List<Gallery> matchingGallery = artworks.searchGallery(keywordYouSearch);

                // Display the matching artwork
                if (!matchingGallery.isEmpty()) {
                    System.out.println("Matching Gallery:");
                    for (Gallery gallery : matchingGallery) {
                        System.out.println("Gallery ID: " + gallery.getGalleryID());
                        System.out.println("Name: " + gallery.getName());
                        System.out.println("Description: " + gallery.getDescription());
                        System.out.println("Location: " + gallery.getLocation());
                        System.out.println("Curator: " + gallery.getCurator());
                        System.out.println("OpeningHours: " + gallery.getOpeningHours());
                        
                        
                    }
                } else {
                    System.out.println("No matching gallery found.");
                }

                
                break;
           		 
            default:
                System.out.println("Exit");
        }
        
            sc.close();
           
            }
	
        
            catch (SQLException  e) {
                // Handle exceptions, log or print the error
                e.printStackTrace();
            }
        finally {
            // Close the database connection in the 'finally' block
            connector.closeConnection();
        }
        String connectionString = DBPropertyUtil.getConnectionString("config.properties");
        System.out.println("Retrieved Connection String: " + connectionString);

        if (connectionString != null) {
            Connection connection = DBConnUtil.getConnection(connectionStrings, username, password);
            System.out.println("Database Connection String: " + connectionString );
        
         if(connection !=null){
             System.out.println("Connected to the Database");  
         }
         else{
             System.out.println("Failed to Establish A database Connection");
         }
            
        }
        
        else {
            System.out.println("Failed to retrieve the database connection string.");
        }
        
        
         }

}
