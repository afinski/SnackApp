# SnackApp
Imagine that you work for SnackTruck, which runs food trucks that sell a selection of snacks.  Instead of having one line, SnackTruck wants to have a pool 
of phones and tablets each truck can pass around, so multiple people can order snacks at once.  Your job is to write the app that will go on those order-
taking devices.  
This app will present a list of snacks which can be veggies [text in green color] or non-veggies [text in red color] with a checkbox:

French-fries [ ]

Milk-shake [ ]

Chicken-burger [ ]

Veggie-burger [ ]

Priority One Use Case:

The customer should be able to select snacks from the list, and submit orders.

1. A user can select a set of snacks.
2. Pressing a Submit button at the bottom of the UI will finish the order and show the summary (a list of selected snacks) in a dialog.
3. Once the order summary dialog is dismissed, all selections will return to default, preparing the app for the next order.
Note: The network service for actually placing the order isn't built yet, so just put a stub & comment where you'd put the call to send the order.

Priority Two Use Case:

The customer should be able to filter the snack list by snack type.
1. There will be two checkboxes at the top of the app, Veggies and Non-Veggies.  Both are checked by default.
2. When the Veggies checkbox is checked, Veggie snacks are shown in the main list.  When the Veggies checkbox is unchecked, Veggie snacks aren't
shown.  Same for Non-Veggie.
3. All requirements of the priority 1 use case are met.

Priority Three Use Case:

The truck operator should be able to add more snacks to the list.
1. There must be an “add” action in the action bar at the top of the app
2. When a user selects the “add” action, a dialog is displayed.
3. The dialog should have a toggle for veggie/non-veggie, and a text field to enter the new snack name
4. The dialog should have “Save” and “Cancel” buttons.
5. When the user hits “Save”, the dialog is dismissed, and the user’s new snack is added to the list.
6. When the user hits “Cancel”, the dialog is dismissed.
