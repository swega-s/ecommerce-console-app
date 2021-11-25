# ecommerce-console-app

4 types of users:

guest -> a customer who simply browses without an account                               
buyer -> a registered candidate(customer) who comes to the app to buy things online                             
seller -> a registered candidate who comes to the app to sell things                      
admin -> a registered candidate and one who controls management of the software                           

FEATURES AVAILABLE: 

An item is,                                                                                                 
    -> a wrapper created for defining a product we chose                                                                          
    -> it contains only the details of a product, not the product itself.                                                             
    -> each item represents one product we chose.                                                                 

An order can,                                                                                                           
    -> have multiple items(different items with an order number)                                                                
    -> can be cancelled with order id                                                                                       
    
A shopping cart is,                                                                                                       
    -> available for a customer(guest/ buyer) - EACH for containing the chosen items                                            
    -> items will be removed once the order is placed                                                                                                               
                                                                                            
A guest can,                                                                                                   
      -> search with the catalog for products                                                                               
      -> add item to their temporary cart (to place an order he/she must create an account. otherwise their cart items. Account creation is done with the help of admin                                                                                                            
      
An admin can,                                                                                                                   
      -> create a buyer/ seller account                                                                                                 
      -> can change an account status                                                                                             
      -> can create and edit product categories                                                                                   
      
      
A buyer can,                                                                                            
      -> search with the catalog for products                                                                         
      -> can place an order which has atleast one item in it                                                                        
      -> can make a payment for the order                                                                                   
      -> can view what items are there in their cart                                                                            
      -> can view their orders                                                                                            
      -> can cancel an order if its shipment status is PENDING                                                                                
      -> can remove/ edit quantity of items in their cart                                                                                   
      -> can change their account password

A seller can,                                                                                                             
      -> can add product for available product categories in the store                                                                              
      -> can change their account password                                                                                              
      
CONTROLLERS:                                                                                                                                
1. Member controller -> this has the members db. Member include an admin or a buyer or a seller                                                                 
2. Product controller -> this has the products db                                                                                       
3. ProductCategory Controller -> this has the product category db

Authentication:                                                                                                      
      A login class -> to login to the app after authenticating user with the given username and password.                                        
      
