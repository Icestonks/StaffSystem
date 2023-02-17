Easy to use

**DEFAULT CONFIG**

```yml
staffmode:
  permission: "staff"

  #
  # ➜ Reload Command
  # ➜ Reloader hele configgen!
  # ➜ Command: /staff reload
  #
  reload-permission: staff.reload

  prefix: "&8[ &c&lSTAFF &f&lSYSTEM &8] "

  staffmode-messages:
    enabled-staffmode: "&fDu aktiverede &astaff-mode"
    disabled-staffmode: "&fDu deaktiverede &cstaff-mode"

staffchat:
  enabled: true
  prefix: "&8[ &c&lSTAFF &f&lCHAT &8] "
  item-name: "&c&lSTAFF &f&lCHAT"

  #
  # ➜ Spillerens navn: %player%
  # ➜ Spillerens besked: %message%
  #
  enabled-staffchat: "&7Du aktiverede &fstaff-chat"
  disabled-staffmode: "&7Du deaktiverede &fstaff-chat"
  staff-chat-message: "&f%player% &8» &f%message%"


vanish:
  enabled: true
  prefix: "&8[ &c&lSTAFF &f&lVANISH &8] "
  item-name: "&c&lVANISH &f&lMODE"

  enabled-vanish: "&7Du gik i &fvanish&7!"
  disabled-vanish: "&7Du gik ud af &fvanish&7!"

  #
  # ➜ Personer med disse permissions, kan se alle selvom de går i vanish
  #
  show-always:
    - "staff.admin"

teleport-menu:
  enabled: true
  prefix: "&8[ &c&lTELPORT &f&lMENU &8] "
  item-name: "&c&lTELPORT &f&lMENU"

  #
  # ➜ Teleported player: %player%
  #
  messages:
    teleport-message: "&7Du teleportede til &f%player%"

speed:
  #
  # ➜ Spillerens nuværende speed: %speed%
  # ➜ Spillerens nye speed: %new-speed%
  #
  enabled: true
  prefix: "&8[ &c&lSPEED &f&lSYSTEM &8] "
  item-name: "&c&lSPEED &f&l%speed%"

  messages:
    new-speed: "&7Din speed er nu sat til &f%new-speed%"


thru:
  enabled: true
  prefix: "&8[ &c&lTHRU &f&lSYSTEM &8] "
  item-name: "&c&lTHRU &f&lTELEPORT"

  messages:
    thru-success: "&7Whooosh!"
    thru-fail: "&cDer blev ikke fundet nogen ledig plads foran dig"

ride:
  enabled: true
  prefix: "&8[ &c&lRIDE &f&lSYSTEM &8] "
  item-name: "&c&lRIDE &f&lSYSTEM"

  #
  # ➜ passenger: %passenger%
  # ➜ vehicle: %vehicle% (spilleren som passenger ridder på)
  #
  messages:
    passenger-message: "&7Du ridder nu på &f%vehicle%&7!"
    vehicle-message: "&f%passenger% &7ridder nu på dig!"

fly:
  enabled: true
  prefix: "&8[ &c&lFLY &f&lMODE &8] "
  item-name: "&c&lFLY &f&lMODE"

  messages:
    enabled-fly: "&7Du aktiverede &ffly&7!"
    disable-fly: "&7Du deaktiverede &ffly&7!"
```
