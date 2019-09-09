# Unit Converter - Android

This is a standard unit converter, which converts between unit types in a common category.

## Project Goals
For this implementation, my goals were as follows:
- [x] Make the app as quick and simple as possible
- [x] Populate UI dynamically based based on spinner selection.  

### Simple UX
To provide instant feedback in the UI, instead of listening for a button click to trigger the conversion, a TextWatcher is used to identify change in the input EditText field, and triggers the conversion process each time a number is entered. The conversion process runs for _all_ output units at once, rather than requiring the user to choose only one.

### Dynamic UI
The second goal was to avoid statically defining UI elements that depend on user selection, and instead create (inflate) and populate them dynamically based on an array of Strings corresponding to the unit type category.

The app works, and can be expanded without great effort to include additional unit categories. At the moment, conversion only works for Temperature; selection of Length in the unit category spinner will correctly populate the input unit spinner and inflate the output fields, but I have not yet had time to enter the formulas to make the conversion itself happen.

## Daily Log

* Aug 29 - Sept 2
Just starting to play around with Android Studio, getting a feel for how to create buttons, move fields around, etc.

* Sept 3
  * adjust layout size-by-side; string format output two decimal places
  * refactor: back to one Unit enum... break up convert from and to in convert() by fromType variable
  * refactor: split enum (trial); move conversion to method
    
* Sept 4
  * configure basic auto-convert onKeyUp
  * change layout again...
    
* Sept 5
  * implement basic spinner populated by Unit enum; add Kelvin and additional formulas; manually add output fields, but the others don't work yet
  * moved onKeyX listeners to addTextChangedListener on num_in, now auto convert/update works for all keys, including backspace/forward delete
    
* Sept 6 - 7
  * Begin working on array-based unit containers
  * minus sign no longer crashes app
  * update layout
  * Fix convert to work within parentLinearLayout loop to set each unit type
  * loop through output layouts IN PROGRESS
  * inflate output fields in loop
    
* Sept 8
  * Refactor main computation to Unit class, fix odds and ends
  * Remove toolbar, replace with full sized unit category spinner; other minor layout updates
  * Refactor to fix spinner configuration, mechanics now seem to be working
  * notifyDataSetChanged() not working on inputUnitArrayAdapter
  * still fixing null ptr TextView bug when changing unit category
