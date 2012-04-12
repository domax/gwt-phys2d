GWT Phys2D
==========

Forewords
---------

This project was created JFF to try and play with different GWT technolodies - 
like e.g. HTML5 canvas and UiBinder - and train myself in the following:

*   TDD development - it's easy to read and think about TDD, 
    but quite difficult to live with it w/o a habit.
*   Using Eclipse as IDE (I like NetBeans, but for GWT development 
    Eclipse is really the best).
*   Using Git as the VCS and GitHub as one of server implementations of it.

Though, I'd like that these experiments will be useful, so I decided to
make something interesting and helpful - like 2D engine for canvas-related
games.

Project Structure
-----------------

As I mentioned before, this project is Eclipse-related - it means just that
I used project structure as it was proposed by Eclipse, but I tried to
avoid commiting any things that has some specific information from my
computer (full paths, libs that provided by Eclipse and so on), so this
project should be fixed a little bit before you can use it.

Just do following after you import checkouted sources:

1.  If you see an error: 
    *"The GWT SDK JAR gwt-servlet.jar is missing in the WEB-INF/lib directory"*
    then simply right-click on it and select "Quick Fix" command,
    then allow Eclipse to copy this library into proper place.

2.  If you see warnings, like that: *"The following classpath entry
    '.../gwt-phys2d/Phys2D/lib/gwt-validation-2.0.jar' will not be available on
    the server's classpath"*, then do like in previous section - select
    "Quick Fix" and copy corresponding library to WEB-INF/lib.
    
3.  Just one library shouldn't be copied to WEB-INF/lib - 
    it is easymock-3.1.jar. Though, if it will be copied there, it's OK.
    But it'd be better simply ignore warning this way: 
    open project's properties, select there "Google/Web Application"
    and add there for warning suppressing all the jar files that are mentioned
    in Markers view (now it is only easymock-3.1.jar).

Then use this stuff for your needs.

### Phys2D Module ###

I placed all the engine-related functionality into separate GWT module 
that has a name Phys2D (well actually full name is 
`com.dominichenko.pet.gwt.phys2d.Phys2D`).

_I didn't yet created separate script to build JAR file for it, but I'll
do it ASAP._

### Demo Application ###

This project also has demo app where I will gather all functionality
supported by Phys2D, so you can see how it looks like.

### Unit Tests ###

Also, I said about TDD - so all the most important code is covered by 
unit tests. I'm consciously avoiding GWTTestCase - my experience shows
that GWT is quite mature already, so it's safe now to make UT just for
java classes.
Though, I keep in mind several differences betweeen Java and Javascript
- e.g. in regexp computations - but it's rare and very specific case.
 
### Javadocs ###

I'm trying to describe everything that is more or less important by Javadocs, 
so if you'll generate documantation, you have good description of all project
stuff.

_Of course, that build script will include Javadoc generating also._


