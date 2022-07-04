javac -d bin src/gui/buttons/ImageButtonHighlighted.java src/gui/buttons/TextButtonHighlighted.java src/gui/dialogs/MenuScopeDialog.java src/gui/MainFrame.java src/gui/panels/AbstractPanel.java src/gui/panels/ChefPanel.java src/gui/panels/CookPanel.java src/gui/panels/MainPanel.java src/gui/panels/WaiterPanel.java src/main/Main.java src/restaurant/Dish.java src/restaurant/Menu.java src/restaurant/MenuScope.java src/restaurant/MenuUtil.java src/restaurant/Order.java src/restaurant/OrderUtil.java
xcopy src\assets bin\assets /E/H/C/I/Y
cd bin
jar -cvfm ../RestaurantManager.jar ../src/META-INF/MANIFEST.MF gui/buttons/*.class gui/dialogs/*.class gui/*.class gui/panels/*.class main/*.class restaurant/*.class  assets/*.png
cd ..
pause
