package com.Tej.Servcie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.DTO.RestDTO;
import com.Tej.Entity.Address;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Repository.AddressRepo;
import com.Tej.Repository.RestaurantRepo;
import com.Tej.Repository.Userrepo;
import com.Tej.Request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImp implements RestaurantService {

	@Autowired
	private RestaurantRepo restRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private Userrepo userepo;
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address adres=addressRepo.save(req.getAddress());
		Restaurant res=new Restaurant();
		res.setAddress(adres);
		res.setContac(req.getContac());
		res.setCuisineType(req.getCusionType());
		res.setDescription(req.getDescription());;
		res.setImages(req.getImages());
		res.setName(req.getName());
		res.setOpeningHourse(req.getOpeningHourse());
		res.setRegisterTime(LocalDateTime.now());
		res.setOwner(user);
		
		return restRepo.save(res);
	}

	@Override
	public Restaurant updateRestaurant(CreateRestaurantRequest Updatereq, Long Id) throws Exception {
		Restaurant res=getRestaurantById(Id);
		
		if(res.getCuisineType()!=null) {
			res.setCuisineType(Updatereq.getCusionType());
		}
		if(res.getDescription()!=null) {
			res.setDescription(Updatereq.getDescription());
		}
		if(res.getName()!=null) {
			res.setName(Updatereq.getName());
		}
		if(res.getAddress()!=null) {
			res.setAddress(Updatereq.getAddress());
		}
		return restRepo.save(res);
	}

	@Override
	public void deleteResttaurant(Long Id) throws Exception {
		
		Restaurant res=getRestaurantById(Id);
		if(res==null) {
			throw new Exception("No resturant found");
		}else
			restRepo.delete(res);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		
		return restRepo.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurnt(String Keyword) {
		
		return restRepo.findBySearchQuery(Keyword);
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userID) throws Exception {
		
		Optional<Restaurant> opt=restRepo.findById(userID);
		if(opt==null) {
			throw new Exception("Restruant not found with user Id "+userID);
		}
		return opt.get();//this is returing the Resutrant bean
	}

	@Override
	public Restaurant getRestaurantById(Long ResID) throws Exception {
		
		Restaurant opt=restRepo.getReferenceById(ResID);
		if(opt==null) {
			throw new Exception("Restruant not found with user Id "+ResID);
		}
		return opt;//this is returing the Resutrant bean
	}

	//This is Good
	@Override
	public RestDTO addtoFav(Long restID, User user) throws Exception {
		
		Restaurant restu=getRestaurantById(restID);
		
		RestDTO dto=new RestDTO();
		
		dto.setDescription(restu.getDescription());
		dto.setImage(restu.getImages());
		dto.setTitle(restu.getName());
		dto.setId(restID);
		
		if(user.getFavo().contains(dto)) {//if the user as fav than remove it 
			user.getFavo().remove(dto);// remove that DTO for the user object not all fav
		}
		else { //add the fav
			user.getFavo().add(dto);
		}
		
		userepo.save(user);//save it i mean the user
		return dto; //return DTO
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		
		Restaurant rest=getRestaurantById(id);
		rest.setOpen(!rest.isOpen());
		return restRepo.save(rest);
	}

}
