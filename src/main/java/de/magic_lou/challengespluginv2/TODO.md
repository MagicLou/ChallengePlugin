Test:
    ChunkBreaking
    ChunkWalking
    RandomEffectOnBlock fail
    ForceBiom Es muss sich nur ein Spieler im Biom befinden

Implement Challenge:
    IceChallenge
    LevelBorder
    SpeedChallenge
    VampireChallenge
    no-up
    no-down
    schlagenReihenfolge

Einheitlich dt ODER en



shift Description-stuff in extern File

HeftDings
    fertig
    items

help inv

mc difficulty in GUI-inv

randomChallenge
TheBrainFRandomizer
TheUltraRandomizer

Battles

RandomChunkGeneration

shared Inv


no up/down challenge
Rain challenge
klau random item du must erraten challenge
Magic challenge
EntityMorph Challenge

datenbank





SonarLintError die ich aber so brauche:
    Disabled SonarLintErrors: must return Interface instead of Implementation (Map|EnumMap)
                              packageName only lowerCase


    RandomBlockLook 55ff            wird aber so gebraucht
    RandomCrafting                  Deprecation Ingredients -> Choice
    RandomEffectOnBlock             EnumMap
    RandomEntitySpawn               EnumMap
    RandomEntitySpawnOnKill         EnumMap
    RandomInventory                 EnumMap
    RandomItem                      Deprecation ItemInHand -> ActiveItem
    Randomizer                      EnumMap + Deprecation ItemInHand -> ActiveItem
    ChallengeRegister               can't remember
    RandomMobDrop                   NOT_FIXED: duplication with RandomMobLoot + EnumMap
    RandomMobLoot                   NOT_FIXED: duplication with RandomMobDrop + EnumMap + Deprecation getMaxHealth -> getHealth
    ChallengeManagerGUI             NOT_FIXED: can be converted to 'record'
    SelectedGlowing                 NOT_FIXED: copied Work & Rule Nr.1: Never touche a running System
    BackPack                        NOT_FIXED: Don't understand computeIfAbsent + Deprecation ItemInHand -> ActiveItem
    EmptyChunkGenerator             NOT_FIXED: copied Work & Rule Nr.1: Never touche a running System
    NoGravity                       NOT_FIXED: Don't understand computeIfAbsent + Boolean.TRUE.equals + static Random
    PlayerManager                   NOT_FIXED: sus call? + removed Getter of spectators
    PauseStopListener
    Timer
    Utils                           EnumMap
    UtilsGUI

    Main

    RandomChallenge
    SharedInv
    DataBase
