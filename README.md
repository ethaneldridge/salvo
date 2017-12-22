# salvo
Purpose: This project provides programmatic control to the open-source boardgame engine Vassal (vassalengine.org) via a TCP/IP socket.

Approach:  The vassalengine library is used as-is.  The vassal classes are either extended through inheritence or intercepted through Aspect Oriented Programming (AOP).  The TCP/IP server embedded in the runtime accepts commands and returns JSON data results.

It is currently running behind a webserver (a separate project to programmatically control the engine) on demo.vassalontheweb.com/index.html

Why "salvo"? The name "salvo" was chosen because it uses many of the same letters as "vassal" and maintains a military reference.
