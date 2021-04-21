# ctfservice-zuul-gateway
Dieses Repository enthält den Zuul Gateway Microservices. Sämtlicher Anfragen durch die SecurityTokenConfig mittels JWT authentifiziert.
Die Tokens werden im Authenticationservice beim Login erzeugt.

Authentication:
- Public can access:
  - any content (home,faq, rules)
- User can...
  - access everything available to the public 
  - access a list of all challenges
  - WIP
- Admin can... 
  - create/update/delete pages/challenges
  - access everything
  - WIP
