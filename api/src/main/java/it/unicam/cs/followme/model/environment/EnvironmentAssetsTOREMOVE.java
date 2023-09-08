package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.utilities.FollowMeShapeChecker;

public class EnvironmentAssets implements FollowMeShapeChecker {
    @Override
    public boolean checkParameters(String[] args) {
       if(args[0] != null &&  args[0].charAt(0) == '_' &&
          args[2] != null &&  args[3] != null && args[4] != null){
               if(args[1] == "CIRCLE")
                   return true;
               if(args[1] == "RECTANGLE" && args[5] != null)
                   return true;
                }
        return false;
    }
}
