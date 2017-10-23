# Sell Inventory
![Sell Inventory tab][1]

The *Sell Inventory* view will allow the sale of existing inventory.

In order to add an item to the order, two pieces of data are needed:

* **Quantity:** The number of items of this specific product that the customer is ordering.
* **Product ID:** The unique ID for the product that is being sold.

When both have been input into their respective two boxes at the top of the view,
 clicking *Add item* or pressing enter with the cursor in the *ID* text field will
 add the item to the order, so long as the quantity and product ID are valid. The
 program will automatically calculate the total price for each line item, and keep
 a running total at the bottom of the view.

**NOTE: As of right now, it is not possible to delete items from the list. If you**
 **want to remove an item from the order, close and reopen the view.**

Once the order is complete, clicking on *Complete Order* will create an invoice
 that will be stored in the database, and the on-hand quantity will be detracted.

**NOTE: As of right now, it is not possible to view the completed invoices.**
 **This will be implemented in a later version.**

[1]: ../img/sellinv.png "Sell Inventory tab"
