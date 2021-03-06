# EverNote

# Resume

The app has been designed following MVP (https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern. 
This pattern assumes perfectly all the functionality, waranty minimun couple between different layers and ensures communication.

First of all, two interfaces that are required and provided by the layers in the Model-View-Presenter have been designed. 
This design ensures loose coupling between the layers in the app's MVP-based architecture.
This Interfaces are MVCLogin and MVCNotes.

The EverNote API used in this project: http://dev.evernote.com/doc/ and is based on thift network protocol RPC and included in 
project as dependence (gradle).

# Structure
Project has 4 main packets, 3 of them related with MVP-based architecture:
- Model: contains classes that play the "Model" role in the Model-View-Presenter (MVP)
  pattern by defining an interface for providing data that will be
  acted upon by the "Presenter" and "View" layers in the MVP pattern.
  It implements the MVP/L.ProvidedModelOps
- Presenter: contains classes that play the "Presenter" role in the Model-View-Presenter
 (MVP) pattern by acting upon the Model and the View, i.e.,
  retrieves data from the Model (e.g., NOtes) and
  display in the View (e.g., Notest list).
  Implements  MVP.ProvidedPresenterOps and
  MVP.RequiredPresenterOps to decouple the MVP layers.
- View: All view related classes: Activities, fragments.. LoginActivity and ItemListActivity implement "RequiredViewOps"

# Feature Login:

Using Evernote by Sandbox login this feature is available. After logged in if you press back button you can 
choose keep logged in or not (dialog).

# Feature list ordering: 
well known menu in android. 
Retained this info in retained fragment over state changes (turn screen etc)

# Feature notes list and note detail:

Design pattern: the well known lisview/detail for android.

Notes in List are displayed in a RecyclerView widget because is a more advanced and flexible version of ListView (see https://developer.android.com/training/material/lists-cards.html).

We uses a simple cardview for concrete element in list.

Note content: Depending on screen size: new activity for smalls screen and fragment for big screens.

# Feature create note by keyboard:

Pressing floating button "+", new activity is called, then user is able to create note by keyoboard or by hand writing in screen.

# Feature create note by text recognition OCR:

Android Text Recognition API has been used to implement this feature.

It is litle hard to do but really easy to implement (Basically a GestureOverlayView and OnGesturePerformedListener.).

The "hard metal" part is the gestures library creation...must be done character by character... It has been created using "gesture tool", then pulled from device and finally added as a resource at res/raw/gestures.txt.

We use same "create note" interface to improve user experience.

# Other features:

-State change managed using "retained fragment".

-Support for different screen sizes.

-All calls are Async to thrift RPC, passing corresponding callbacks (provided by Evernote API).

-Swipe view to refresh the list elements.

# Video
https://youtu.be/35WF0Syps1Q








