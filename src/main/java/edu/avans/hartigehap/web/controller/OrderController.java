package edu.avans.hartigehap.web.controller;




import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.UUID;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.service.DiningTableService;
import edu.avans.hartigehap.service.OrderService;
import edu.avans.hartigehap.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
/**
 * If I were a design pattern, I would be a Singleton.
 * I did singlely, ton's of work
 * 
 * @author Maurits
 *
 */

@Controller
@Slf4j
public class OrderController {
	 @Autowired
	 private RestaurantService restaurantService;
	 @Autowired
	 private OrderService orderService;

	//Get methode voor de order pagina
    @RequestMapping(value = { "/restaurants/{restaurantName}/order" }, method = RequestMethod.GET)
    public String giveOrder(@PathVariable("restaurantName") String restaurantName,Model uiModel, HttpServletResponse response, HttpServletRequest request) {
    	
    	 String OrderID = null;
    	Cookie[] cookies = request.getCookies();
    	for(int i = 0; i< cookies.length ; ++i){
    		
            if(cookies[i].getName().equals("HartigeHapOrderID")){
            	OrderID = cookies[1].getValue();
            	 log.info(cookies[1].getValue());
                break;
            }
           
            if(i==cookies.length-1){
            	 log.info("Geen cookie gevonden");
            	 Cookie newCookie = createCookie();
            	 OrderID = newCookie.getValue();
            	response.addCookie(newCookie);
            }
        }
    	
    	setOrderModel(restaurantName, uiModel, OrderID);
        return "hartigehap/order";

    }
    //Generate a cookie with an unique UUID as identifier for online orders
    public Cookie createCookie(){
    	//Create a random UUID
    	UUID randomUUID =UUID.randomUUID();
    	Cookie cookie = new Cookie("HartigeHapOrderID", randomUUID.toString());
    	//path for entire application
    	cookie.setPath("/hh/");
    	return cookie;
    }
    
    //Haalt het restaurant object op
    public void setOrderModel(String restName,Model uiModel, String onlineOrderID){
    	Restaurant restaurant = restaurantService.fetchWarmedUp(restName);
        uiModel.addAttribute("restaurant", restaurant);
        Order order = orderService.getOrderByOnlineID(onlineOrderID);
        uiModel.addAttribute("order", order);
    }
    //Post methode voor de order toevoegen
    @RequestMapping(value = { "/restaurants/{restaurantName}/order/menuItems" }, method = RequestMethod.POST)
    public String addMenuItem(@PathVariable("restaurantName") String restaurantName, Model uiModel, @RequestParam String menuItemName,@CookieValue("HartigeHapOrderID") String orderID) {
    	 setOrderModel(restaurantName, uiModel, orderID);
    	 orderService.addOrderItem(orderID, menuItemName,restaurantName);
    	return "redirect:/restaurants/"+ restaurantName+"/order";
    }
    //PUT voor de verandering van een order
    @RequestMapping(value = "/restaurants/{restaurantName}/order", method = RequestMethod.PUT)
    public String prepareBill(@PathVariable("restaurantName") String restaurantName,Model uiModel,@CookieValue("HartigeHapOrderID") String orderID) {
    	setOrderModel(restaurantName, uiModel, orderID);
    	return "redirect:/restaurants/"+ restaurantName+"/delivery";

        }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/order/menuItems/{menuItemName}", method = RequestMethod.DELETE)
    public String deleteMenuItem(@PathVariable("restaurantName") String restaurantName,
            @PathVariable("menuItemName") String menuItemName, Model uiModel,@CookieValue("HartigeHapOrderID") String orderID) {



        orderService.deleteOrderItem(orderID, menuItemName);

        return "redirect:/restaurants/"+ restaurantName+"/order";
    }
    

    
}

