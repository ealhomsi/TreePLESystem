/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.treeple.model;
import java.util.*;

// line 33 "../../../../../TreePLEPersistence.ump"
// line 23 "../../../../../TreePLE.ump"
public class Municipality
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Municipality> municipalitysByName = new HashMap<String, Municipality>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Municipality Attributes
  private String name;

  //Municipality Associations
  private List<Tree> trees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Municipality(String aName)
  {
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    trees = new ArrayList<Tree>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      municipalitysByName.remove(anOldName);
    }
    municipalitysByName.put(aName, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public static Municipality getWithName(String aName)
  {
    return municipalitysByName.get(aName);
  }

  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public Tree getTree(int index)
  {
    Tree aTree = trees.get(index);
    return aTree;
  }

  public List<Tree> getTrees()
  {
    List<Tree> newTrees = Collections.unmodifiableList(trees);
    return newTrees;
  }

  public int numberOfTrees()
  {
    int number = trees.size();
    return number;
  }

  public boolean hasTrees()
  {
    boolean has = trees.size() > 0;
    return has;
  }

  public int indexOfTree(Tree aTree)
  {
    int index = trees.indexOf(aTree);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(int aDiameter, Location aTreeLocation)
  {
    return new Tree(aDiameter, aTreeLocation, this);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    Municipality existingMunicipality = aTree.getMunicipality();
    boolean isNewMunicipality = existingMunicipality != null && !this.equals(existingMunicipality);
    if (isNewMunicipality)
    {
      aTree.setMunicipality(this);
    }
    else
    {
      trees.add(aTree);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    //Unable to remove aTree, as it must always have a municipality
    if (!this.equals(aTree.getMunicipality()))
    {
      trees.remove(aTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeAt(Tree aTree, int index)
  {  
    boolean wasAdded = false;
    if(addTree(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeAt(Tree aTree, int index)
  {
    boolean wasAdded = false;
    if(trees.contains(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeAt(aTree, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    municipalitysByName.remove(getName());
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
  }

  // line 35 "../../../../../TreePLEPersistence.ump"
   public static  void reinitializeUniqueName(List<Municipality> municipalities){
    municipalitysByName = new HashMap<String, Municipality>();
		for (Municipality municipality : municipalities) {
			municipalitysByName.put(municipality.getName(), municipality); 
		}
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}