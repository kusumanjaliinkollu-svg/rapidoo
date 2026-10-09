package com.alpha.RapidoCustomerService;

import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.RapidoCustomerService.dto.Addressdto;
import com.alpha.RapidoCustomerService.dto.Bookingdto;
import com.alpha.RapidoCustomerService.dto.CustomerRideRequestDto;
import com.alpha.RapidoCustomerService.dto.Customerdto;
import com.alpha.RapidoCustomerService.dto.FareDto;
import com.alpha.RapidoCustomerService.dto.ResponseStructure;
import com.alpha.RapidoCustomerService.dto.RideRequestDto;
import com.alpha.RapidoCustomerService.dto.SearchDestinationLocationDto;
import com.alpha.RapidoCustomerService.dto.SelectRideDto;
import com.alpha.RapidoCustomerService.entity.Address;
import com.alpha.RapidoCustomerService.entity.Booking;
import com.alpha.RapidoCustomerService.entity.Coordinates;
import com.alpha.RapidoCustomerService.entity.Customer;
import com.alpha.RapidoCustomerService.exception.BookingNotFound;
import com.alpha.RapidoCustomerService.exception.CustomerNotFound;
import com.alpha.RapidoCustomerService.repository.Adressrepo;
import com.alpha.RapidoCustomerService.repository.BookingRepo;
import com.alpha.RapidoCustomerService.repository.CoordinateRepo;
import com.alpha.RapidoCustomerService.repository.CustomerRepo;

@Service
public class Customerservice {
   
	@Autowired
	private BookingRepo bookrepo;
	
	@Autowired
	private CustomerRepo customerrepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Adressrepo addrepo;
	
	@Autowired
	private CoordinateRepo coordinateRepo;
	
	@Autowired
	private RedisTemplate<String,Map<String,Object>> redistemplate;
	
	@Autowired
	@Qualifier("geoRedisTemplate")
	private RedisTemplate<String, String> geoRedisTemplate;
	
	
	@Autowired
	@Qualifier("rideRequestRedisTemplate")
	private RedisTemplate<String, RideRequestDto> rideRequestRedisTemplate;
	
	
	@Autowired
	@Qualifier("bookingRedisTemplate")
	private RedisTemplate<String, String> bookingRedisTemplate;
	
	public String genotp(long mobile) {
		Customer cc=  customerrepo.findByMobile(mobile).orElseThrow(()-> new CustomerNotFound());
	    int id=cc.getId();
	    int otp = id % 10000;

	    return String.format("%04d", otp);
	}
	
	public ResponseStructure<Customer> createcust(Customerdto crdto) {
	       Customer c=new Customer();
	       
	       c.setName(crdto.getName());
	       c.setMobile(crdto.getMobile());
	       c.setEmail(crdto.getEmail());
	       c.setGender(crdto.getGender());
	       customerrepo.save(c);
	       c.setOtp(genotp(crdto.getMobile()));
	       customerrepo.save(c);
	       
	       ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
	       rs.setStatuscode(HttpStatus.CREATED.value());
	       rs.setMessage("customer data saved successfully");
	       rs.setData(c);
	       
	        return rs;
		
	}

	public ResponseStructure<Customer> findcust(int id) {
		
		Customer c=customerrepo.findById(id).orElseThrow(()->new CustomerNotFound());
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
	       rs.setStatuscode(HttpStatus.FOUND.value());
	       rs.setMessage("customer finded successfully");
	        rs.setData(c);
	        return rs;
		
	}

	public ResponseStructure<Customer> deletecusto(int id) {
		Customer c=customerrepo.findById(id).orElseThrow(()->new CustomerNotFound());
		customerrepo.delete(c);
		 ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
	       rs.setStatuscode(HttpStatus.FOUND.value());
	       rs.setMessage("customer deleted successfully");
	        rs.setData(null);
	        return rs;
	}

	public Object SearchDropLoca(String searchkey) {
		String url="https://us1.locationiq.com/v1/search?key=pk.537c8269e4fbd6567cb7e4e345452aea&q="+ searchkey +"%20&format=json&";
		
		ArrayList<Map<String,Object>> response=restTemplate.getForObject(url, ArrayList.class);
		List<SearchDestinationLocationDto> searchDestinationLocationDtolist=new ArrayList<SearchDestinationLocationDto>();
		
		for (Map<String,Object> object : response) {
			String lat=(String)object.get("lat");
			double lati=Double.parseDouble(lat);
			String lon=(String)object.get("lon");
			double longi=Double.parseDouble(lon);
			SearchDestinationLocationDto destinationLocationDto=new SearchDestinationLocationDto((String)object.get("display_name"),lati,longi);
			
			searchDestinationLocationDtolist.add( destinationLocationDto);
		}
		 
		return searchDestinationLocationDtolist;
	}

	
	public List<Addressdto> getAdressDetails(double slati,double slong, double dlati, double dlong){
		String sorceurl = "https://us1.locationiq.com/v1/reverse?key=pk.537c8269e4fbd6567cb7e4e345452aea&lat="+slati+"&lon="+slong+"&format=json&";
		String destiurl = "https://us1.locationiq.com/v1/reverse?key=pk.537c8269e4fbd6567cb7e4e345452aea&lat="+dlati+"&lon="+dlong+"&format=json&";
		
//		getting whole date
		Map<String, Object> source=restTemplate.getForObject(sorceurl, Map.class);
		Map<String, Object> destination=restTemplate.getForObject(destiurl, Map.class);
		
		// getting Source Adress from whole date
		Map<String, Object> sadd =(Map<String, Object>) source.get("address");
		String sstreet = (String) sadd.get("road");
		String slandmark = (String) sadd.get("suburb");
		String scity = (String) sadd.get("city");
		int spincode = Integer.parseInt((String) sadd.get("postcode"));
		String sstate = (String) sadd.get("state");
		String scountry = (String) sadd.get("country");
		
		// getting Destination Adress from whole date
		Map<String, Object> dadd =(Map<String, Object>) source.get("address");
		String dstreet = (String) dadd.get("road");
		String dlandmark = (String) dadd.get("suburb");
		String dcity = (String) dadd.get("city");
		int dpincode = Integer.parseInt((String) dadd.get("postcode"));
		String dstate = (String) dadd.get("state");
		String dcountry = (String) dadd.get("country");
		
		List<Addressdto> add = new ArrayList<Addressdto>();
		
		Addressdto a1 = new Addressdto();
		a1.setBuildingname(null);
		a1.setCity(scity);
		a1.setCountry(scountry);
		a1.setLandmark(slandmark);
		a1.setPincode(spincode);
		a1.setState(sstate);
		a1.setStreet(sstreet);
		
		Addressdto a2 = new Addressdto();
		a2.setBuildingname(null);
		a2.setCity(dcity);
		a2.setCountry(dcountry);
		a2.setLandmark(dlandmark);
		a2.setPincode(dpincode);
		a2.setState(dstate);
		a2.setStreet(dstreet);
		
		add.add(a1);
		add.add(a2);
		
		return add;
	}
	
	public FareDto selectrider(int cusid, SelectRideDto srdto) {
		Customer c=customerrepo.findById(cusid).orElseThrow(()->new CustomerNotFound());
		Coordinates s=srdto.getSourceCordinate();
		Coordinates d=srdto.getDestinationCordinate();
		String source=s.getLongi()+","+s.getLati();
		String destination=d.getLongi()+","+d.getLati();
		String url = "https://us1.locationiq.com/v1/directions/driving/" +source +";"+ destination +"?key=pk.537c8269e4fbd6567cb7e4e345452aea&steps=true&alternatives=true&geometries=polyline&overview=full&";
		Map<String, Object> response=restTemplate.getForObject(url,Map.class);
		List<Map<String, Object>> routes=(List<Map<String, Object>>) response.get("routes");
		Map<String, Object> route=routes.get(0);
		Double distance= ((Number)route.get("distance")).doubleValue();
		Double km=distance/1000.0;
		List<Map<String, Object>> waypoints=(List<Map<String, Object>>) response.get("waypoints");
		String pickup=(String)waypoints.get(0).get("name");
		String drop=(String)waypoints.get(1).get("name");
		
		String finalsource = "name : " + pickup + " , coordinates: " + source;
		String finaldesti = "name : "+drop+" , coordinates: "+destination;
		Map<String, Double> fares=new LinkedHashMap<>();
		fares.put("Bike", km*10.0);
		fares.put("Auto", km*20.0);
		fares.put("car", km*30.0);
		
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("source", finalsource);
		value.put("destination", finaldesti);
		value.put("Fares", fares);
		
		String id=String.valueOf(cusid);
		redistemplate.opsForValue().set(id, value);
	    FareDto fdto=new FareDto();
		fdto.setSource(finalsource);
		fdto.setDestination(finaldesti);
		fdto.setFare(fares);
		return fdto;
	}

	

//	public ResponseStructure<String> confirride(int cid, String vehc) {
//		Customer c=customerrepo.findById(cid).orElseThrow(()->new CustomerNotFound());
//		String id = String.valueOf(cid);
//		  Map<String, Object> redata= redistemplate.opsForValue().get(id);
//		  
//		  String source = (String) redata.get("source");
//		  String destination = (String) redata.get("destination");
//		  
//		  String sourceCoordinates =source.split("coordinates:")[1].trim();
//		  String destinationCoordinates =destination.split("coordinates :")[1].trim();
//		  String[] sourceCoord = sourceCoordinates.split(",");
//		  String[] destinationCoord = destinationCoordinates.split(",");
//		  
//		  double slati = Double.parseDouble(sourceCoord[0]);
//		  double slong = Double.parseDouble(sourceCoord[1]);
//		  double dlati = Double.parseDouble(destinationCoord[0]);
//		  double dlong = Double.parseDouble(destinationCoord[1]);
//		  
//		  
//		  
////		  getting source and destination latitude details from redis to pass to getAdressDetails() method to get List<Adreesdto> 
//		  String des = (String) redata.get("destination");
//		  String sou = (String) redata.get("source");
//		  
//		  Address pickloc = new Address();
//		  List<Addressdto> addresses =getAdressDetails(slati, slong, dlati, dlong);
//			Addressdto sadd = addresses.get(0);
//			Addressdto dadd = addresses.get(1);
//		  
////		  saving source adreess
//		  Address soadd = new Address();
//		  soadd.setBuildingname(sadd.getBuildingname());
//		  soadd.setCity(sadd.getCity());
//		  soadd.setCountry(sadd.getCountry());
//		  soadd.setLandmark(sadd.getLandmark());
//		  soadd.setPincode(sadd.getPincode());
//		  soadd.setState(sadd.getState());
//		  soadd.setStreet(sadd.getStreet());
//		  addrepo.save(soadd);
//		  
////		  saving destination adress
//		  Address disadd = new Address();
//		  disadd.setBuildingname(dadd.getBuildingname());
//		  disadd.setCity(dadd.getCity());
//		  disadd.setCountry(dadd.getCountry());
//		  disadd.setLandmark(dadd.getLandmark());
//		  disadd.setPincode(dadd.getPincode());
//		  disadd.setState(dadd.getState());
//		  disadd.setStreet(dadd.getStreet());
//		  addrepo.save(disadd);
//		  
//		  Booking b= new Booking();
//		  LocalDate today=LocalDate.now();
//		  DateTimeFormatter date=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		  String bdate = today.format(date);
//		  b.setBookingdate(bdate);
//		  LocalTime now = LocalTime.now();
//		  DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
//		  String btime = now.format(time);
//		  b.setBookingtime(bdate);
//		  b.setBookingtime(btime);
//		  b.setCustomer(c);
//		  b.setDestinationloc(disadd);
//		  b.setDroptime(null);
//		  
//		  Map<String, Object> fares =(Map<String, Object>) redata.get("Fares");
//		  String vfair = String.valueOf(fares.get(vehc));
//		  b.setFare(vfair);
//		  b.setPaymenttype("Pending");
//		  b.setPickuploc(soadd);
//		  b.setPickuptime(null);
//		  b.setRiderid(1);
//		  b.setVehicletype(vehc);
//		  bookrepo.save(b);
//		  redistemplate.delete(id);
//		  ResponseStructure<String> rs = new ResponseStructure<String>();
//		  rs.setStatuscode(HttpStatus.ACCEPTED.value());
//		  rs.setMessage("Booking Done");
//		  rs.setData("Booked");
//		  return rs;
//	}
	
	public ResponseStructure<Map<String, Object>> confirmride(int cid,String vehc,int riderId, int rideId, String idempotencyId) {
	   Customer c = customerrepo.findById(cid) .orElseThrow(() -> new CustomerNotFound());
	   Optional<Booking> existingBooking = bookrepo.findByIdempotencyId(idempotencyId);
	   if (idempotencyId == null || idempotencyId.isBlank()) {
		   	ResponseStructure<Map<String, Object>> rs =new ResponseStructure<>();
		    rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
		    rs.setMessage("Idempotency ID is required");
		    rs.setData(null);        
		    return rs;
	}
	   if (existingBooking.isPresent()) {

	        Booking b = existingBooking.get();

	        Map<String, Object> bookingData = new HashMap<>();

	        bookingData.put("bookingId", b.getId());
	        bookingData.put("fare", b.getFare());
	        bookingData.put("customerName", b.getCustomer().getName());
	        bookingData.put("customerMobileNumber", b.getCustomer().getMobile());
	        bookingData.put("otp", b.getCustomer().getOtp());     
	        ResponseStructure<Map<String, Object>> rs =  new ResponseStructure<>();
	        rs.setStatuscode(HttpStatus.OK.value());
	        rs.setMessage("Booking Already Exists");
	        rs.setData(bookingData);
	        return rs;
	    }        
	    String rideKey = "ride:" + rideId;
	    Map<String, Object> redata = redistemplate.opsForValue().get(rideKey);
	    if (redata == null) {	       

	        ResponseStructure<Map<String, Object>> rs =  new ResponseStructure<>();
	        rs.setStatuscode(HttpStatus.NOT_FOUND.value());
	        rs.setMessage("Ride Not Found");
	        rs.setData(null);
	        return rs;
	        
	    }
	    String status = String.valueOf(redata.get("status"));
	    if (!status.equals("REQUESTED")) {

	        ResponseStructure<Map<String, Object>> rs = new ResponseStructure<>();
	        rs.setStatuscode(HttpStatus.CONFLICT.value());
	        rs.setMessage("Ride Already Accepted");
	        rs.setData(null);
             return rs;
	    }

	    double slati = Double.parseDouble( String.valueOf(redata.get("pickupLatitude")));
	    double slong = Double.parseDouble( String.valueOf(redata.get("pickupLongitude")));   
	    double dlati = Double.parseDouble(String.valueOf(redata.get("dropLatitude")));
	    double dlong = Double.parseDouble( String.valueOf(redata.get("dropLongitude")));
	    Coordinates sourceco = new Coordinates();   
	    sourceco.setLati(slati);
	    sourceco.setLongi(slong);
	    Coordinates destinationco = new Coordinates();
	    destinationco.setLati(dlati);
	    destinationco.setLongi(dlong);
	    coordinateRepo.save(sourceco);
	    coordinateRepo.save(destinationco);
	    Booking b = new Booking();
	    LocalDate today = LocalDate.now();

	    DateTimeFormatter date =DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bdate = today.format(date);

	    b.setBookingdate(bdate);

	   
	    LocalTime now = LocalTime.now();

	    DateTimeFormatter time =DateTimeFormatter.ofPattern("HH:mm:ss");
	    String btime = now.format(time);

	    b.setBookingtime(btime);

	   
	    b.setCustomer(c);

	    b.setDestinationloc(String.valueOf(dlati) + "," + String.valueOf(dlong));
        b.setPickuploc(String.valueOf(slati) + "," + String.valueOf(slong));
        b.setDroptime(null);

	    b.setFare("0");

	    b.setPaymenttype("Pending");

	    b.setPickuptime(null);

	    b.setRiderid(riderId);

	    b.setVehicletype(vehc);

	    b.setSource(sourceco);

	    b.setDestination(destinationco);

	   
	    b.setStatus("CONFIRMED");


	    bookrepo.save(b);

	    bookingRedisTemplate.opsForValue()  .set("booking:" + b.getId(),String.valueOf(rideId)    );

	    System.out.println("Booking ID = " + b.getId());
	    System.out.println("Ride ID = " + rideId);

	    String test =bookingRedisTemplate.opsForValue().get("booking:" + b.getId());
        System.out.println( "Redis booking mapping = " + test);
        redata.put("status", "ACCEPTED");
        redata.put("riderId", riderId);
        redata.put("bookingId", b.getId());

		   redistemplate.opsForValue().set(rideKey, redata);
	       Map<String, Object> bookingData = new HashMap<>();
	       bookingData.put(  "bookingId", b.getId());     
	       bookingData.put("fare",b.getFare());
	       bookingData.put("customerName", c.getName());
		   bookingData.put(  "customerMobileNumber",  c.getMobile());
		   bookingData.put( "otp", c.getOtp());    
		   ResponseStructure<Map<String, Object>> rs =new ResponseStructure<>();
		   rs.setStatuscode( HttpStatus.ACCEPTED.value());
		   rs.setMessage("Booking Done");
		   rs.setData(bookingData);        
		   return rs;
	}

public List<String> requestRide(int cid, String vehc, CustomerRideRequestDto dto) {
	Customer c = customerrepo.findById(cid).orElseThrow(()-> new CustomerNotFound());
	Point p = new Point(dto.getPickupLongitude(), dto.getPickupLatitude());
	Circle ci = new Circle(p,new Distance(2, Metrics.KILOMETERS));
	GeoResults<GeoLocation<String>> results = geoRedisTemplate.opsForGeo().search(vehc.toLowerCase(), ci);
	int rideId = new Random().nextInt(9000) + 1000;
	Map<String, Object> rideData = new HashMap<>();

	    rideData.put("rideId", rideId);
	    rideData.put("customerId", cid);
	    rideData.put("vehicleType", vehc);
	    rideData.put("pickupLatitude", dto.getPickupLatitude());
	    rideData.put("pickupLongitude", dto.getPickupLongitude());
	    rideData.put("dropLatitude", dto.getDropLatitude());
	    rideData.put("dropLongitude", dto.getDropLongitude());
	    rideData.put("status", "REQUESTED");
	    rideData.put("riderId", null);
	    redistemplate.opsForValue().set("ride:" + rideId, rideData);
	RideRequestDto rd = new RideRequestDto();
	rd.setRideId(rideId);
	rd.setCustomerId(cid);
	rd.setVehicleType(vehc);
	rd.setPickupLongitude(dto.getPickupLongitude());
	rd.setPickupLatitude(dto.getPickupLatitude());
	rd.setDropLatitude(dto.getDropLatitude());
	rd.setDropLongitude(dto.getDropLongitude());
	List<String>  sent = new ArrayList<String>();
	for (GeoResult<GeoLocation<String>> res : results) {
		String riderId = res.getContent().getName();
		String channel = "ride-request:" + riderId;
		rideRequestRedisTemplate.convertAndSend(channel, rd);
		System.out.println("Request pushed to " + riderId);
		sent.add(riderId);
	}
	
	return sent;
}

public ResponseStructure<CustomerRideRequestDto> getBookingLocation(int bookingId) {
	 Booking b = bookrepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking Not Found"));

	    CustomerRideRequestDto dto = new CustomerRideRequestDto();
        dto.setPickupLatitude(b.getSource().getLati());
	    dto.setPickupLongitude(b.getSource().getLongi());
        
	    dto.setDropLatitude(b.getDestination().getLati());
	    dto.setDropLongitude(b.getDestination().getLongi());

	    ResponseStructure<CustomerRideRequestDto> rs = new ResponseStructure<>();

	    rs.setStatuscode(HttpStatus.OK.value());
	    rs.setMessage("Booking Location Found");
	    rs.setData(dto);
        return rs;
}


public ResponseStructure<String> verifyOtp(int bookingId,String otp) {
	Booking b = bookrepo.findById(bookingId) .orElseThrow(() -> new BookingNotFound());
	String customerOtp = b.getCustomer().getOtp();
    ResponseStructure<String> rs = new ResponseStructure<>();
    if (customerOtp.equals(otp)) {
    	System.out.println("OTP MATCHED");
        LocalTime now = LocalTime.now();
        DateTimeFormatter time =DateTimeFormatter.ofPattern("HH:mm:ss");
        b.setStatus("STARTED");
        b.setPickuptime(now.format(time));
        bookrepo.save(b);
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("OTP Verified. Ride Started");
        rs.setData("STARTED");

    } else {
    	System.out.println("OTP NOT MATCHED");
        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage("Invalid OTP");
        rs.setData("OTP Verification Failed");
    }

    return rs;

}

public List<Booking> ridehistoryy(int cid, String status) {
	if(status.equalsIgnoreCase("ALL")) {
        return bookrepo.findByCustomerId(cid);
    }
	return bookrepo.findByCustomerIdAndStatus(cid, status);	
}

	




}
