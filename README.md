# AltFinder

A spigot plugin made for EarthMC. Not released yet.

## Coming features: 
Check accounts against the database of stolen accounts MCLeaks.

Automatically get all accounts from the same ip, with all usernames they have used to connect listed on each account.

Manage alts to split the accounts of friends and family so they do not trigger warnings for alternate accounts anymore.

Accounts that have once connected from the same ip and then been verified as being different players will still trigger warnings if any additional accounts connect from the same ip in the future.

Comparison of how many times two accounts have connected fom the same ip and a timeline overview.

## Current Commands

`/mcleaks <player>` - Checks if a player is in a list of confirmed mcleaks accounts. These accounts have been 100% confirmed to be stolen and used by MCLeaks. More info here: https://github.com/TheMrGong/MCLeaksApiClient.

`/ip (player)` - Prints a player's IP.

## Current Permissions

`altfinder.mcleaks` - Gives access to the `/mcleaks` command.
`altfinder.mcleaks.notify` - Displays a notification if a player who is using an mcleaks account joins.
`altfinder.ip.self` - Allows `/ip` command to display own IP. (Given by default).
`altfinder.ip.other` - Allows `/ip` command to display another palyer's IP.
