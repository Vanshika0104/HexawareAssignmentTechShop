package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exception.ArtworkNotFoundException;
import exception.UserNotFoundException;


import entity.Artwork;
import entity.Gallery;

public class VirtualArtGalleryImpl implements IVirtualArtGallery {
	private Connection connection;
	
	 public VirtualArtGalleryImpl(Connection connection) {
		 this.connection= connection;
	 }

	 public VirtualArtGalleryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addArtwork(Artwork artwork) {
			// TODO Auto-generated method stub
		 try {
			 
			String INSERT_ARTWORK_SQL = "INSERT INTO Artwork (Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?,CURRENT_DATE, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ARTWORK_SQL)) {
	            preparedStatement.setString(1, artwork.getTitle());
	            preparedStatement.setString(2, artwork.getDescription());
	            preparedStatement.setString(3, artwork.getMedium());
	            preparedStatement.setString(4, artwork.getImageURL());
	            preparedStatement.setInt(5, artwork.getArtistID());

	            int rowsAffected = preparedStatement.executeUpdate();

	            return rowsAffected > 0; 
	        }
			
	 
			
           } catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		return false;
		
		 }
	 
	 

	@Override
	public boolean updateArtwork(Artwork artwork) {
		// TODO Auto-generated method stub
		String UPDATE_ARTWORK_SQL = "UPDATE Artwork SET Title=?, Description=?, Medium=?, ImageURL=?, ArtistID=? WHERE ArtworkID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ARTWORK_SQL)) {
            preparedStatement.setString(1, artwork.getTitle());
            preparedStatement.setString(2, artwork.getDescription());
            preparedStatement.setString(3, artwork.getMedium());
            preparedStatement.setString(4, artwork.getImageURL());
            preparedStatement.setInt(5, artwork.getArtistID());
            preparedStatement.setInt(6, artwork.getArtworkID());

            int rowsAffected =preparedStatement.executeUpdate();

            
            return rowsAffected > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
    }

	

	@Override
	public boolean removeArtwork(int artworkID) {
		// TODO Auto-generated method stub
		String DELETE_ARTWORK_SQL = "DELETE FROM Artwork WHERE ArtworkID=? LIMIT 1 ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ARTWORK_SQL)) {
            preparedStatement.setInt(1, artworkID);

            int rowsAffected = preparedStatement.executeUpdate();

            
            return rowsAffected > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Artwork getArtworkById(int artworkID) throws ArtworkNotFoundException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Artwork WHERE ArtworkID = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, artworkID);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                // Create and populate the Artwork object
	                Artwork artwork = new Artwork();
	                artwork.setArtworkID(resultSet.getInt("ArtworkID"));
	                artwork.setTitle(resultSet.getString("Title"));
	                artwork.setDescription(resultSet.getString("Description"));
	                artwork.setCreationDate(resultSet.getDate("CreationDate"));
	                artwork.setMedium(resultSet.getString("Medium"));
	                artwork.setImageURL(resultSet.getString("ImageURL"));
	                artwork.setArtistID(resultSet.getInt("ArtistID"));

	                return artwork;
	            }
	            else {
	                throw new ArtworkNotFoundException("Artwork with ID " + artworkID + " not found");
	            }
	        }
	    } catch (SQLException e) {
	        
	        System.err.println("Error retrieving artwork with ID " + artworkID + ": " + e.getMessage());
	    }

	    
	    return null;
	}
	

	@Override
	public List<Artwork> searchArtworks(String keyword) {
		// TODO Auto-generated method stub
		List<Artwork> matchingArtworks = new ArrayList<>();
        String sql = "SELECT * FROM Artwork WHERE Title LIKE ? OR Description LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setArtworkID(resultSet.getInt("ArtworkID"));
                    artwork.setTitle(resultSet.getString("Title"));
                    artwork.setDescription(resultSet.getString("Description"));
                    artwork.setCreationDate(resultSet.getDate("CreationDate"));
                    artwork.setMedium(resultSet.getString("Medium"));
                    artwork.setImageURL(resultSet.getString("ImageURL"));
                    artwork.setArtistID(resultSet.getInt("ArtistID"));
                    matchingArtworks.add(artwork);
                }
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return matchingArtworks;
    }

	@Override
	public boolean addArtworkToFavorite(int userID, int artworkID) {
		// TODO Auto-generated method stub
		String INSERT_FAVORITE_SQL = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAVORITE_SQL)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, artworkID);

            int rowsAffected = preparedStatement.executeUpdate();

            
            return rowsAffected > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public List<Artwork> getUserFavoriteArtworks(int userID) throws UserNotFoundException {
		// TODO Auto-generated method stub
		List<Artwork> favoriteArtworks = new ArrayList<>();
        String sql = "SELECT A.* FROM Artwork A " +
                     "JOIN User_Favorite_Artwork UFA ON A.ArtworkID = UFA.ArtworkID " +
                     "WHERE UFA.UserID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setArtworkID(resultSet.getInt("ArtworkID"));
                    artwork.setTitle(resultSet.getString("Title"));
                    artwork.setDescription(resultSet.getString("Description"));
                    artwork.setCreationDate(resultSet.getDate("CreationDate"));
                    artwork.setMedium(resultSet.getString("Medium"));
                    artwork.setImageURL(resultSet.getString("ImageURL"));
                    artwork.setArtistID(resultSet.getInt("ArtistID"));

                    favoriteArtworks.add(artwork);
                }
                else {
                	throw new UserNotFoundException("User with ID " + userID + " not found");
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            
        }

        return favoriteArtworks;
    }

	@Override
	public boolean removeArtworkFromFavorite(int userID, int artworkID) {
		// TODO Auto-generated method stub
		 String DELETE_FAVORITE_SQL = "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";

		    try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FAVORITE_SQL)) {
		        preparedStatement.setInt(1, userID);
		        preparedStatement.setInt(2, artworkID);

		        int rowsAffected = preparedStatement.executeUpdate();

		        return rowsAffected > 0;
		    } catch (SQLException e) {
		        
		        e.printStackTrace();
		        return false;
		    }
	}

	@Override
	public boolean createNewGallery(Gallery gallery) {
		// TODO Auto-generated method stub
		try {
			 
			String INSERT_GALLERY_SQL = "INSERT INTO Gallery (Name, Description, Location, Curator, OpeningHours) VALUES (?, ?,?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GALLERY_SQL)) {
	            preparedStatement.setString(1, gallery.getName());
	            preparedStatement.setString(2, gallery.getDescription());
	            preparedStatement.setString(3, gallery.getLocation());
	            preparedStatement.setInt(4, gallery.getCurator());
	            preparedStatement.setString(5, gallery.getOpeningHours());

	            int rowsAffected = preparedStatement.executeUpdate();

	            return rowsAffected > 0; 
	        }
			
	 
			
           } catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		return false;
		
		
	}

	@Override
	public boolean updateGallery(Gallery gallery) {
		// TODO Auto-generated method stub
		String UPDATE_GALLERY_SQL = "UPDATE Gallery SET Name=?, Description=?, Location=?, Curator=?, OpeningHours=? WHERE GalleryID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GALLERY_SQL)) {
            preparedStatement.setString(1, gallery.getName());
            preparedStatement.setString(2, gallery.getDescription());
            preparedStatement.setString(3, gallery.getLocation());
            preparedStatement.setInt(4, gallery.getCurator());
            preparedStatement.setString(5, gallery.getOpeningHours());
            preparedStatement.setInt(6, gallery.getGalleryID());

            int rowsAffected =preparedStatement.executeUpdate();

            
            return rowsAffected > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean removeGallery(int galleryID) {
		// TODO Auto-generated method stub
		String DELETE_GALLERY_SQL = "DELETE FROM Gallery WHERE GalleryID=? LIMIT 1 ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GALLERY_SQL)) {
            preparedStatement.setInt(1, galleryID);

            int rowsAffected = preparedStatement.executeUpdate();

            
            return rowsAffected > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public List<Gallery> searchGallery(String keyword) {
		// TODO Auto-generated method stub
		List<Gallery> matchingGallery = new ArrayList<>();
        String sql = "SELECT * FROM Gallery WHERE Name LIKE ? OR Description LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Gallery gallery = new Gallery();
                    gallery.setGalleryID(resultSet.getInt("GalleryID"));
                    gallery.setName(resultSet.getString("Name"));
                    gallery.setDescription(resultSet.getString("Description"));
                    gallery.setLocation(resultSet.getString("Location"));
                    gallery.setCurator(resultSet.getInt("Curator"));
                    gallery.setOpeningHours(resultSet.getString("OpeningHours"));
                    
                    matchingGallery.add(gallery);
                }
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return matchingGallery;
	}




	




	
	}
	


