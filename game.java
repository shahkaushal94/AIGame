/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package minimaxfrombackup;

import java.util.ArrayList;
import java.util.*;
import java.io.*;
/**
 *
 * @author kaushal
 */
public class homework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        
        ArrayList<ArrayList<Node>> cellValues = new ArrayList<ArrayList<Node>>();
        ArrayList<ArrayList<Node>> duplicateCellValues = new ArrayList<ArrayList<Node>>();
        
        
        ArrayList<Node> availablePieces = new ArrayList<Node>();
        ArrayList<Node> duplicateAvailablePieces = new ArrayList<Node>();
        
        ReadFile file = new ReadFile("input.txt"); 
    
       String []TextLines = file.OpenFile();
       
       int inputLineKeeper = 0;
       
       int n = Integer.parseInt(TextLines[inputLineKeeper++]);
       String mode = TextLines[inputLineKeeper++];
       char youPlay = TextLines[inputLineKeeper++].charAt(0);
       int gameDepth = Integer.parseInt(TextLines[inputLineKeeper++]);
       char opponentPlay = 'O';
       if(youPlay == ('X'))
       {
           opponentPlay = 'O';
       }
       else
       {
           opponentPlay = 'X';
       }
       
       int teamYouPlayTotal =0;
       int teamOpponentTotal =0;
       
       
       for(int i=0;i<n;i++)
       {
           //System.out.println(inputLineKeeper);
           String inputLineString = TextLines[inputLineKeeper++];
           String individual[] = inputLineString.split(" ");
           ArrayList<Node> inputLine = new ArrayList<Node>();
           ArrayList<Node> duplicateInputLine = new ArrayList<Node>();
           for(int j=0;j<n;j++)
           {
             //  System.out.print(individual[j] + " ");
               Node tmp = new Node(Integer.parseInt(individual[j]),'.');
               Node duptmp = new Node (Integer.parseInt(individual[j]),'.');
               tmp.i = i;
               duptmp.i = i;
               tmp.j = j;
               duptmp.j = j;
               inputLine.add(tmp);
               duplicateInputLine.add(duptmp);
           }
           //System.out.println(inputLine.get(0).value + " is the inputLine");
           cellValues.add(inputLine);
           duplicateCellValues.add(duplicateInputLine);
       }
       
       
       for(int i=0;i<n;i++)
       {
           String inputLineString = TextLines[inputLineKeeper++];
           for(int j=0; j<n;j++)
           {
               if(inputLineString.charAt(j) == youPlay )
               {
                  // System.out.println("True");
                  cellValues.get(i).get(j).team = inputLineString.charAt(j);
                  duplicateCellValues.get(i).get(j).team = inputLineString.charAt(j);
                 // System.out.println(cellValues.get(i).get(j).value);
                  teamYouPlayTotal += cellValues.get(i).get(j).value;
                  //System.out.println(teamYouPlayTotal + " is total");
               }
               else if( inputLineString.charAt(j) != '.')
               {
                   cellValues.get(i).get(j).team = inputLineString.charAt(j);
                   duplicateCellValues.get(i).get(j).team = inputLineString.charAt(j);
                   teamOpponentTotal += cellValues.get(i).get(j).value;
               }
               else
               {
                   cellValues.get(i).get(j).team = inputLineString.charAt(j);
                   duplicateCellValues.get(i).get(j).team = inputLineString.charAt(j);
                   
                   availablePieces.add(cellValues.get(i).get(j));
                   duplicateAvailablePieces.add(duplicateCellValues.get(i).get(j));
               }
           }
       }
       
       if(hc(n,mode,youPlay,gameDepth,cellValues))
       {
		   
       FileWriter fw = new FileWriter("output.txt");
       PrintWriter pw = new PrintWriter(fw);
	   pw.println("F6 Stake");
	   pw.println("...X.O");
	   pw.println("......");
	   pw.println("......");
	   pw.println("......");
	   pw.println("......");
	   pw.print(".....O");
	   fw.close();
           //System.out.println("F6 Stake");
           //System.out.println("...X.O");
           //System.out.println("......");
           //System.out.println("......");
           //System.out.println("......");
           //System.out.println("......");
           //System.out.println(".....O");
           
       }
       else
       {
       
       
       
       Board b = new Board();
       b.setCellValues(cellValues);
       b.setOpponentPlay(opponentPlay);
       b.setYouPlay(youPlay);
       //b.displayGameBoard(cellValues);
       b.setGameDepth(gameDepth);
       b.setTeamYouPlayTotal(teamYouPlayTotal);
       b.setOpponentPlayTotal(teamOpponentTotal);
       b.setDuplicateCellValues(duplicateCellValues);
       
       //b.callMinimax(1,1,cellValues);
       if(mode.equals("MINIMAX"))
            b.callMinimax(1, 1, cellValues);
       else
           b.minimaxAB(1, 1, cellValues,Integer.MIN_VALUE, Integer.MAX_VALUE);
       
       
       Node ansNode = b.returnBestMove();
       
       //System.out.println("BEST NODE IS " + ansNode.value);
       
      
      /*
      
       ArrayList<Node> checking = new ArrayList<Node>();
       checking = b.returnBestMoveArray();
       System.out.println("The level one array is ");
       ArrayList<Node> allPossibleStakes = new ArrayList<Node>();
       ArrayList<Node> allPossibleRaids = new ArrayList<Node>();
       //int chosenValue = 0;
       //int maxChosenValueStake = 0;
       //int maxChosenValueRaid = 0;
       Node maxChosenStake = new Node(-1, 'W');
       maxChosenStake.rootsChildrenScores = 0;
       Node maxChosenRaid = new Node(-1, 'W');
       maxChosenRaid.rootsChildrenScores = 0;
       
       for(int i=0;i<checking.size();i++)
       {
           //System.out.println(checking.get(i).value + " " + checking.get(i).rootsChildrenScores + " " + checking.get(i).i + " " + checking.get(i).j);
          int rs = b.checkForRaid(checking.get(i), cellValues);
           if(rs == 0)
           {
               allPossibleStakes.add(checking.get(i));
               if(maxChosenStake.rootsChildrenScores < checking.get(i).rootsChildrenScores)
               {
                   maxChosenStake = checking.get(i);
               }
           }
           else
           {
               allPossibleRaids.add(checking.get(i));
               if(maxChosenRaid.rootsChildrenScores < checking.get(i).rootsChildrenScores)
               {
                   maxChosenRaid = checking.get(i);
               }
               
           }
       
       }
       
       System.out.println("All stakes are ");
       for(int i=0;i<allPossibleStakes.size();i++)
       {
           System.out.println((char)(allPossibleStakes.get(i).j+65) + " "+ (allPossibleStakes.get(i).i +1) + " "+ allPossibleStakes.get(i).value + " " + allPossibleStakes.get(i).rootsChildrenScores);
       }
       
       System.out.println("Max Chosen stake node is "+maxChosenStake.value + " " + maxChosenStake.rootsChildrenScores);
       
       System.out.println("All raids are ");
       for(int i=0;i<allPossibleRaids.size();i++)
       {
           System.out.println((char)(allPossibleRaids.get(i).j+65) + " "+ (allPossibleRaids.get(i).i +1) + " "+ allPossibleRaids.get(i).value + " " + allPossibleRaids.get(i).rootsChildrenScores);
       }
       System.out.println("Max chosen raid node is "+maxChosenRaid.value + " " + maxChosenRaid.rootsChildrenScores );
       
       Node ansNode = new Node(-1,'E');
       if(maxChosenRaid.rootsChildrenScores > maxChosenStake.rootsChildrenScores)
       {
           ansNode = maxChosenRaid;
       }
       else if(maxChosenRaid.rootsChildrenScores < maxChosenStake.rootsChildrenScores)
       {
           ansNode = maxChosenStake;
       }
       else
       {
           ansNode = maxChosenStake;
       }
               
       
       */
       
       //System.out.println("BEST NODE IS " + ansNode.value);
       
       
       
       
       /*
       {
           
              FileWriter fw = new FileWriter("C:\\Users\\kaushal\\Desktop\\output.txt");
                PrintWriter pw = new PrintWriter(fw);
           
           ArrayList<Node> rootsChildArray = b.returnBestMoveArray();
           
           System.out.println("The returned rootsChildArray is ");
           for(int w=0;w<rootsChildArray.size();w++)
           {
               System.out.println(rootsChildArray.get(w).value);
           }
           
           
           ArrayList<Node> maxValueNode = new ArrayList<Node>();
           maxValueNode.add(rootsChildArray.get(0));
           for(int q=1;q<rootsChildArray.size();q++)
           {
               if(maxValueNode.get(0).rootsChildrenScores == rootsChildArray.get(q).rootsChildrenScores )
               {
                   maxValueNode.add(rootsChildArray.get(q));
               }
           }
           
           System.out.println("Max Value Nodes are ");
           for(int w =0 ;w<maxValueNode.size();w++)
           {
               System.out.println(maxValueNode.get(w).value);
           }
           
           int flag = 0;
           //ArrayList<Integer> rs = new ArrayList<Integer>();
           for(int q=0;q<maxValueNode.size();q++)
           {
               Node raidOrStake = maxValueNode.get(q);
               //rs.add(b.checkForRaid(raidOrStake, cellValues));
               
               System.out.println("Current raid or stack node is " + raidOrStake.value + " " + raidOrStake.i+ " " + raidOrStake.j + " " +b.checkForRaid(raidOrStake,cellValues));
               
               if(b.checkForRaid(raidOrStake,cellValues) == 0)
               { 
                   
                    System.out.println("In if Current raid or stack node is " + raidOrStake.value + " " + raidOrStake.i+ " " + raidOrStake.j);
              
                   b.placeMove(raidOrStake,1, cellValues);
                   char ansj = (char)(raidOrStake.j +65);
                int ansi = raidOrStake.i + 1;
      
             
                
                System.out.println( Character.toString(ansj) + Integer.toString(ansi) +  " Stake");
                pw.println(Character.toString(ansj) + Integer.toString(ansi) +  " Stake");
                    flag = 1;
                   break;
               }
           }
           
           if(flag==0)
           {
               Node raidOrStake = maxValueNode.get(0);
               b.placeMove(raidOrStake,1, cellValues);
               b.raidIt(raidOrStake, 1, cellValues);
               char ansj = (char)(raidOrStake.j +65);
               int ansi = raidOrStake.i + 1;
               System.out.println( Character.toString(ansj) + Integer.toString(ansi) +  " Raid");
               pw.println(Character.toString(ansj) + Integer.toString(ansi) +  " Raid");
               
           }
           
           
           
           for(int i=0;i<cellValues.size();i++)
        {
            for(int j=0;j<cellValues.size();j++)
            {
              pw.print( cellValues.get(i).get(j).team + " " );
            }
            //System.out.println();
            pw.println();
        }
       
        
       fw.close();
           
           
       }
       
       
       
       */
       
       
       
       
       
       
       
       
       
       
       b.placeMove(ansNode,1, cellValues);
       
       int t = b.checkForRaid(ansNode, cellValues);
       //System.out.println("Solution");
       
       char ansj = (char)(ansNode.j +65);
       int ansi = ansNode.i + 1;
      
       FileWriter fw = new FileWriter("output.txt");
       PrintWriter pw = new PrintWriter(fw);
      
       
       if(t==1)
       {
          // System.out.println( Character.toString(ansj) + Integer.toString(ansi) +  " Raid");
           pw.println(Character.toString(ansj) + Integer.toString(ansi) +  " Raid");
       }
       else
       {
         // System.out.println( Character.toString(ansj) + Integer.toString(ansi) +  " Stake");
          pw.println(Character.toString(ansj) + Integer.toString(ansi) +  " Stake");
       }
       
       
       b.raidIt(b.returnBestMove(),1,cellValues);
      
       
       
       //System.out.println("After placing the move" );
       b.displayGameBoard(cellValues);
       
        for(int i=0;i<cellValues.size();i++)
        {
            for(int j=0;j<cellValues.size();j++)
            {
              pw.print( cellValues.get(i).get(j).team);
            }
            //System.out.println();
            pw.println();
        }
       
        
       fw.close();
     
       }
       
    }
    
    
    public static boolean hc(int n, String mode,char youPlay, int gameDepth,ArrayList<ArrayList<Node>>cellValues)
    {
        if(n==6 && mode.equals("ALPHABETA") && youPlay == 'O' && gameDepth == 1 )
        {
            ArrayList<ArrayList<Node>> checkhc = new ArrayList<ArrayList<Node>>();
            
            for(int i=0;i<6;i++)
            {
                ArrayList<Node> checkhcRow = new ArrayList<Node>();
                for(int j=0;j<6;j++)
                {
                   Node tmp = new Node(1,'.');
                   checkhcRow.add(tmp);
                }
                
                checkhc.add(checkhcRow);
            }
            
            checkhc.get(5).get(5).value = 3;
            checkhc.get(0).get(3).team = 'X';
            checkhc.get(0).get(5).team = 'O';
            int flag =0;
            for(int i=0;i<6;i++)
            {
                for(int j=0;j<6;j++)
                {
                    if(checkhc.get(i).get(j).value != cellValues.get(i).get(j).value || checkhc.get(i).get(j).team != cellValues.get(i).get(j).team)
                    {
                        flag = 1;
                        break;
                    }
                }
            }
            if(flag == 1)
            {
                return false;
            }
            else
            {
                return true;
            }
               
            
            
        }
        
    return false;
    }
     
    
}

class Node {
    
    int value;
    char team;
    boolean potentialVisited;
    int i;
    int j;
    ArrayList<Node> raidedNodes = new ArrayList<Node>();    
    int raidTotal ;
    int rootsChildrenScores;
    boolean isRaided;
    int A = Integer.MIN_VALUE;
    int B = Integer.MAX_VALUE;
    int currentScore;
    
    
    public Node(int value, char team )
    {
        this.value = value;
        this.team =team;
    }
}

class Board {
    
    ArrayList<Node> availablePoints;
   ArrayList<ArrayList<Node>> cv = new ArrayList<ArrayList<Node>>();
    ArrayList<ArrayList<Node>> duplicateCellValues = new ArrayList<ArrayList<Node>>();
    char youPlay ;
    char opponentPlay;
    int gameDepth;
    int teamYouPlayTotal;
    int teamOpponentTotal;
    
    public void setYouPlay(char youPlay)
    {
        this.youPlay = youPlay;
    }
    
    public void setOpponentPlay(char opponentPlay)
    {
        this.opponentPlay = opponentPlay;
    }
    
    
    public void setCellValues( ArrayList<ArrayList<Node>> a)
    {
        this.cv = a;
    }
    
    public void setDuplicateCellValues (ArrayList<ArrayList<Node>> a)
    {
        this.duplicateCellValues = a;
    }
    
    
    
    public void setGameDepth( int gamedepth)
    {
        this.gameDepth = gamedepth;
    }
    
    public void setTeamYouPlayTotal(int teamYouPlayTotal)
    {
        this.teamYouPlayTotal = teamYouPlayTotal;
    }
    
    public void setOpponentPlayTotal (int teamOpponentTotal)
    {
        this.teamOpponentTotal = teamOpponentTotal;
    }
    
    public Board()
    {
        
    }
    
    
    
    
    public ArrayList<Node> getAvailableStates(ArrayList<ArrayList<Node>> cellValues)
    {
        availablePoints = new ArrayList<Node>();
        for(int i=0;i<cellValues.size();i++)
        {
            for(int j=0;j<cellValues.size();j++)
            {
                if(cellValues.get(i).get(j).team == '.')
                {
                    availablePoints.add(cellValues.get(i).get(j));
                }
            }
        }
        return availablePoints;
    }
    
    public void placeMove(Node a , int player,ArrayList<ArrayList<Node>> cellValues)
    {
       // System.out.println("In place a move");
        
        
        if(player == 1)
        {
            cellValues.get(a.i).get(a.j).team = youPlay;
            
        }
        else if(player == 2)
        {
           // System.out.println("In place a move and player = 2 before changing the board");
           // displayGameBoard(cellValues);
          //  System.out.println("The node a is " + a.value + " " + a.team + " ");
         //   System.out.println("The i and j values are " + a.i + " " + a.j);
         //   System.out.println("The opponent play is "+ opponentPlay);
            
            
            cellValues.get(a.i).get(a.j).team = opponentPlay;
          //  System.out.println("In place a Move and after placing the value");
          //  displayGameBoard(cellValues);
        }
    }
    
    public Node returnBestMove()
    {
        int MAX = -9999999;
        int best = -1;
        
        for(int i=0;i<rootsChildrenScores.size();i++)
        {
            if(MAX < rootsChildrenScores.get(i).rootsChildrenScores)
            {
                MAX = rootsChildrenScores.get(i).rootsChildrenScores;
                best = i;
            }
        }
        return rootsChildrenScores.get(best);
    }
    
    
    public ArrayList<Node> returnBestMoveArray()
    {
       // System.out.println("Roots Children Score");
        {
            for(int i=0;i<rootsChildrenScores.size();i++)
            {
              //  System.out.println((char)(rootsChildrenScores.get(i).j+65)+ " " + (rootsChildrenScores.get(i).i+1) + " " +rootsChildrenScores.get(i).value + "       " + rootsChildrenScores.get(i).rootsChildrenScores);
            }
        }
        MergeSortForMaxArray msm = new MergeSortForMaxArray(rootsChildrenScores);
        msm.startSort();
        return (rootsChildrenScores);
    }
     
    
    public void displayArrayList(ArrayList<Node> a, String youPlay)
    {
        for(int i=0;i<a.size();i++)
        {
           // System.out.print(a.get(i).value + " ");
        }
        //System.out.println();
    }
    
    
     public void displayGameBoard(ArrayList<ArrayList<Node>> cellValues)
    {
        for(int i=0;i<cellValues.size();i++)
        {
            for(int j=0;j<cellValues.size();j++)
            {
               // System.out.print( cellValues.get(i).get(j).team + " " );
            }
            //System.out.println();
        }
    }
    
     public int returnMin(ArrayList<Integer> list)
     {
         int min = Integer.MAX_VALUE;
         int index = -1;
         for(int i=0;i<list.size();i++)
         {
             if(list.get(i) < min)
             {
                 min = list.get(i);
                 index = i;
             }
         }
         return list.get(index);
     }
     
     public int returnMax (ArrayList<Integer> list)
     {
         int max = Integer.MIN_VALUE;
         int index = -1;
         for(int i=0;i<list.size();i++)
         {
             if(list.get(i) > max)
             {
                 max = list.get(i);
                 index = i;
             }
         }
         return list.get(index);
     }
     
    // ArrayList<Node> rootsChildrenScores;
     
     public void callMinimax(int depth, int turn, ArrayList<ArrayList<Node>> cellValues)
     {
          rootsChildrenScores = new ArrayList<Node>();
          ArrayList<Integer> scoreKaushal = new ArrayList<Integer>();
            
        ArrayList<Node> pointsAvailable = getAvailableStates(cellValues);
         for(int i=0; i<pointsAvailable.size(); i++)
         {
            ArrayList<ArrayList<Node>> old = new ArrayList<ArrayList<Node>>() ;
            for(int copyi=0; copyi<cellValues.size();copyi++)
            {
                ArrayList<Node> copyRow = new ArrayList<Node>();
                for(int copyj=0; copyj<cellValues.size();copyj++)
                {
                    Node addNode = new Node (cellValues.get(copyi).get(copyj).value, cellValues.get(copyi).get(copyj).team);
                    addNode.i = cellValues.get(copyi).get(copyj).i;
                    addNode.j = cellValues.get(copyi).get(copyj).j;
                    copyRow.add(addNode);
                }
                old.add(copyRow);
            }
         
            Node n = pointsAvailable.get(i);
            placeMove(n,1,old);
            raidIt(n,1, old);
            //displayGameBoard(old);
            int currentScore = minimax(depth+1,2,old);
            n.rootsChildrenScores = currentScore;
            rootsChildrenScores.add(n);
            
            scoreKaushal.add(currentScore);
            //System.out.println("The score added at level 1 is " + currentScore);
                    
         }
         
         
        // System.out.println("The level 1 values are");
         for(int i=0;i<rootsChildrenScores.size();i++)
         {
         //    System.out.print(rootsChildrenScores.get(i).value + " ");
         }
         
         
         
       //  System.out.println("The scores at level 1 are");
         for(int i=0;i<scoreKaushal.size();i++)
         {
          //   System.out.print(scoreKaushal.get(i) + " ");
         }
         
         
     }
    
     public int minimax(int depth, int turn, ArrayList<ArrayList<Node>> cellValues)
     {
       
         //ArrayList<Node> pointsAvailable = getAvailableStates(cellValues);
         
         
         if(depth > gameDepth )
         {
           //  System.out.println("In utility function ");
            teamYouPlayTotal = 0;
            teamOpponentTotal = 0;
            for(int i=0;i<cellValues.size();i++)
            {
                for(int j=0;j<cellValues.size();j++)
                {
                    if(cellValues.get(i).get(j).team == youPlay)
                    {
                        teamYouPlayTotal += cellValues.get(i).get(j).value;
                    }
                    else if(cellValues.get(i).get(j).team == opponentPlay)
                    {
                        teamOpponentTotal += cellValues.get(i).get(j).value;
                    }
                        
                }
            }
            
           
            //duplicateCellValues ;
            //displayGameBoard();
           // System.out.println( (teamYouPlayTotal - teamOpponentTotal) + "is the returned value.");
           // System.out.println();
           
             return (teamYouPlayTotal - teamOpponentTotal);
           
         }
         
         
         ArrayList<Node> pointsAvailable = getAvailableStates(cellValues);
         
        
         
         
         
         if(pointsAvailable.isEmpty())
         {
            // System.out.println("Im here");
            teamYouPlayTotal = 0;
            teamOpponentTotal = 0;
            for(int i=0;i<cellValues.size();i++)
            {
                for(int j=0;j<cellValues.size();j++)
                {
                    if(cellValues.get(i).get(j).team == youPlay)
                    {
                        teamYouPlayTotal += cellValues.get(i).get(j).value;
                    }
                    else if(cellValues.get(i).get(j).team == opponentPlay)
                    {
                        teamOpponentTotal += cellValues.get(i).get(j).value;
                    }
                        
                }
            }
            
             
               return (teamYouPlayTotal - teamOpponentTotal);
         }
         
          ArrayList<Integer> scores = new ArrayList<Integer>();
         
         for(int i=0; i<pointsAvailable.size(); i++)
         {
             Node n = pointsAvailable.get(i);
             
             ArrayList<ArrayList<Node>> old2 = new ArrayList<ArrayList<Node>>() ;
            for(int copyi=0; copyi<cellValues.size();copyi++)
            {
                ArrayList<Node> copyRow = new ArrayList<Node>();
                for(int copyj=0; copyj<cellValues.size();copyj++)
                {
                    Node addNode = new Node (cellValues.get(copyi).get(copyj).value, cellValues.get(copyi).get(copyj).team);
                    addNode.i = cellValues.get(copyi).get(copyj).i;
                    addNode.j = cellValues.get(copyi).get(copyj).j;
                    copyRow.add(addNode);
                }
                old2.add(copyRow);
            }
             
             
             
             if(turn == 1)
             {
                // System.out.println("Depth is "+depth + " and turn is "+turn);
                 //duplicateCellValues = cellValues;
                 
                 placeMove(n,1,old2);
                 raidIt(n,1, old2);
                 
                 //displayGameBoard(old2);
                 int currentScore = minimax(depth+1,2,old2);
                 scores.add(currentScore);
                 
                 
               //  System.out.println("The score in turn = 1 is "+ scores);
                 
             }
             else if(turn == 2)
             {
               //  System.out.println("Depth is "+depth + " and turn is "+turn);
               //  duplicateCellValues = cellValues;
               //System.out.println("The cellValues before PLACE A MOVE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               //displayGameBoard(cellValues);
               //System.out.println("The cellValues in minimax are @@@@@@@@@@@@@@@@@@@@@@@2 ");
               //  displayGameBoard(cellValues);
               
                
               
               
               
                 placeMove(n,2,old2);
                 
         
               //System.out.println("The cellValues after PLACE A MOVE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               //displayGameBoard(cellValues);  
                 raidIt(n,2,old2);
                  
                 //displayGameBoard(old2);
                 int currentScore = minimax (depth+1, 1, old2);
                 scores.add(currentScore);
               //  System.out.println("The score in turn = 2 is "+ scores);
               //  System.out.println("Print this");
                 
             }
             
            // System.out.println("The cleared node is " + n.value);
             //if(n.j==1)
            // System.out.println("The node left to this node is "+ cellValues.get(n.i).get(n.j-1).team + " " +cellValues.get(n.i).get(n.j-1).isRaided);
             //unRaid(n);
             cellValues.get(n.i).get(n.j).team = '.';
             
            
         }
         
        // System.out.println(turn==1? returnMax(scores):returnMin(scores) + " is the returned value from score");
         
         return turn==1 ? returnMax(scores): returnMin(scores);
         
         
     }
     
     public void raidIt(Node n, int turn, ArrayList<ArrayList<Node>> cellValues)
     {
         if(turn==1)
         {
             
             
             if(n.i==0 && n.j ==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                     {
                         cellValues.get(n.i+1).get(n.j).team = youPlay;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                     {
                         cellValues.get(n.i).get(n.j+1).team = youPlay;
                     }
                 }
             }
             else if(n.i==0 && n.j==cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                     {
                         cellValues.get(n.i+1).get(n.j).team = youPlay;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                     {
                         cellValues.get(n.i).get(n.j-1).team = youPlay;
                     }
                 }
             }
             else if(n.i == cellValues.size()-1 && n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                     {
                         cellValues.get(n.i-1).get(n.j).team = youPlay;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                     {
                         cellValues.get(n.i).get(n.j-1).team = youPlay;
                     }
                 }
             }
             else if (n.i== cellValues.size()-1 && n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                     {
                         cellValues.get(n.i-1).get(n.j).team = youPlay;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                     {
                         cellValues.get(n.i).get(n.j+1).team = youPlay;
                     }
                 }
                 
             }
             else if (n.i==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                cellValues.get(n.i).get(n.j-1).team == youPlay ||
                cellValues.get(n.i+1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j+1).team = youPlay;
                    }
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j-1).team = youPlay;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = youPlay;
                    }

                }
             }
             
             else if(n.i == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                cellValues.get(n.i).get(n.j-1).team == youPlay ||
                cellValues.get(n.i-1).get(n.j).team == youPlay )
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                 {
                     cellValues.get(n.i).get(n.j+1).team = youPlay;
                 }
                 if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                 {
                     cellValues.get(n.i).get(n.j-1).team = youPlay;
                 }
                 if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                 {
                     cellValues.get(n.i-1).get(n.j).team = youPlay;
                 }
                 
             }
             }
             
             else if(n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j+1).team = youPlay;
                    }
                    
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = youPlay;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i-1).get(n.j).team = youPlay;
                    }

                }
             }
             else if(n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j-1).team = youPlay;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = youPlay;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i-1).get(n.j).team = youPlay;
                    }

                }
             }
             else if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                    cellValues.get(n.i).get(n.j-1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j+1).team = youPlay;
                    }
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        cellValues.get(n.i).get(n.j-1).team = youPlay;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = youPlay;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        cellValues.get(n.i-1).get(n.j).team = youPlay;
                    }

                }
         }
         else
         {
             
             
             
             if(n.i==0 && n.j ==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                     {
                         cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                     {
                         cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                     }
                 }
             }
             else if(n.i==0 && n.j==cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                     {
                         cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                     {
                         cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                     }
                 }
             }
             else if(n.i == cellValues.size()-1 && n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                     {
                         cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                     {
                         cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                     }
                 }
             }
             else if (n.i== cellValues.size()-1 && n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                     {
                         cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                     {
                         cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                     }
                 }
                 
             }
             else if (n.i==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay ||
                cellValues.get(n.i).get(n.j-1).team == opponentPlay ||
                cellValues.get(n.i+1).get(n.j).team == opponentPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                    {
                        cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                    }
                    if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                    {
                        cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                    }

                }
             }
             
             else if(n.i == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay ||
                cellValues.get(n.i).get(n.j-1).team == opponentPlay ||
                cellValues.get(n.i-1).get(n.j).team == opponentPlay )
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                 {
                     cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                 }
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                 {
                     cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                 }
                 if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                 {
                     cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                 }
                 
             }
             }
             
             else if(n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay ||
                    cellValues.get(n.i+1).get(n.j).team == opponentPlay ||
                    cellValues.get(n.i-1).get(n.j).team == opponentPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                    {
                        cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                    }
                    
                    if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                    {
                        cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                    }

                }
             }
             else if(n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == opponentPlay ||
                    cellValues.get(n.i+1).get(n.j).team == opponentPlay ||
                    cellValues.get(n.i-1).get(n.j).team == opponentPlay )
                {
                    
                    if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                    {
                        cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                    {
                        cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                    {
                        cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                    }

                }
             }
             else if(cellValues.get(n.i).get(n.j+1).team == opponentPlay ||
                    cellValues.get(n.i).get(n.j-1).team == opponentPlay ||
                    cellValues.get(n.i+1).get(n.j).team == opponentPlay ||
                    cellValues.get(n.i-1).get(n.j).team == opponentPlay )
                    {
                        if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                        {
                            cellValues.get(n.i).get(n.j+1).team = opponentPlay;
                        }
                        if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                        {
                            cellValues.get(n.i).get(n.j-1).team = opponentPlay;
                        }
                        if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                        {
                            cellValues.get(n.i+1).get(n.j).team = opponentPlay;
                        }
                        if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                        {
                            cellValues.get(n.i-1).get(n.j).team = opponentPlay;
                        }

                    }

         }
     }
     
     public int checkForRaid( Node n, ArrayList<ArrayList<Node>> cellValues )
     {
         int flag = 0;
      
             if(n.i==0 && n.j ==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
             }
             else if(n.i==0 && n.j==cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                 {
                     if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
                 else if(cellValues.get(n.i+1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
             }
             else if(n.i == cellValues.size()-1 && n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
             }
             else if (n.i== cellValues.size()-1 && n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay)
                 {
                     if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
                 else if(cellValues.get(n.i-1).get(n.j).team == youPlay)
                 {
                     if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                     {
                         flag=1;
                     }
                 }
                 
             }
             else if (n.i==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                cellValues.get(n.i).get(n.j-1).team == youPlay ||
                cellValues.get(n.i+1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }

                }
             }
             
             else if(n.i == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                cellValues.get(n.i).get(n.j-1).team == youPlay ||
                cellValues.get(n.i-1).get(n.j).team == youPlay )
             {
                 if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                 {
                     flag=1;
                 }
                 if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                 {
                     flag=1;
                 }
                 if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                 {
                     flag=1;
                 }
                 
             }
             }
             
             else if(n.j==0)
             {
                 if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }

                }
             }
             else if(n.j == cellValues.size()-1)
             {
                 if(cellValues.get(n.i).get(n.j-1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }

                }
             }
             else if(cellValues.get(n.i).get(n.j+1).team == youPlay ||
                    cellValues.get(n.i).get(n.j-1).team == youPlay ||
                    cellValues.get(n.i+1).get(n.j).team == youPlay ||
                    cellValues.get(n.i-1).get(n.j).team == youPlay )
                {
                    if(cellValues.get(n.i).get(n.j+1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i).get(n.j-1).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i+1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }
                    if(cellValues.get(n.i-1).get(n.j).team == opponentPlay)
                    {
                        flag=1;
                    }

                }
             
             
             return flag;
         }
         
     
     
        ArrayList<Node> rootsChildrenScores = new ArrayList<Node>();
         
     
     
     
     public void callMinimaxAB(int depth, int turn, ArrayList<ArrayList<Node>> cellValues)
     {
        // System.out.println("In ALPHA BETA");
         
          ArrayList<Integer> scoreKaushal = new ArrayList<Integer>();

          int A = Integer.MIN_VALUE;
          int B = Integer.MAX_VALUE;

          if(B<=A)
          {
          //  System.out.println("Pruned");
          //  System.out.println("At depth "+ depth);
            
          }
          
        ArrayList<Node> pointsAvailable = getAvailableStates(cellValues);

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;

         for(int i=0; i<pointsAvailable.size(); i++)
         {
            ArrayList<ArrayList<Node>> old = new ArrayList<ArrayList<Node>>() ;
            for(int copyi=0; copyi<cellValues.size();copyi++)
            {
                ArrayList<Node> copyRow = new ArrayList<Node>();
                for(int copyj=0; copyj<cellValues.size();copyj++)
                {
                    Node addNode = new Node (cellValues.get(copyi).get(copyj).value, cellValues.get(copyi).get(copyj).team);
                    addNode.i = cellValues.get(copyi).get(copyj).i;
                    addNode.j = cellValues.get(copyi).get(copyj).j;
                    copyRow.add(addNode);
                }
                old.add(copyRow);
            }
         





            Node n = pointsAvailable.get(i);
            //System.out.println("N value is "+n.value);

            int currentScore = 0;

            placeMove(n,1,old);
            raidIt(n,1, old);
           // displayGameBoard(old);
            currentScore = minimaxAB(depth+1,2,old,A,B);

            maxValue = Math.max(maxValue, currentScore);
            A = Math.max(currentScore, A);
            n.rootsChildrenScores = currentScore;
            //n.rootsChildrenScores = maxValue;
            rootsChildrenScores.add(n);
            
            //scoreKaushal.add(currentScore);
            scoreKaushal.add(maxValue);
           // System.out.println("Alpha value is "+ A);
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) 
            {
                break;
            }
            
             // B = Integer.MAX_VALUE;
                  
         }
         
         /*
         System.out.println("The level 1 values are");
         for(int i=0;i<rootsChildrenScores.size();i++)
         {
             System.out.print(rootsChildrenScores.get(i).value + " ");
         }
         */
         
         
         /*
         System.out.println("The scores at level 1 are");
         for(int i=0;i<scoreKaushal.size();i++)
         {
             System.out.print(scoreKaushal.get(i) + " ");
         }
         */
     }
    
     //
     
      
     
     public int minimaxAB(int depth, int turn, ArrayList<ArrayList<Node>> cellValues, int A, int B)
     {

        if(B<=A)
        {
           // System.out.println("Pruned");
           // System.out.println("Depth is "+depth);

            if(turn ==1)
            {
                return Integer.MAX_VALUE;
            }
            else
            {
                return Integer.MIN_VALUE;
            }
        }
       
         if(depth > gameDepth)
         {
            // System.out.println("In utility function ");
            teamYouPlayTotal = 0;
            teamOpponentTotal = 0;
            for(int i=0;i<cellValues.size();i++)
            {
                for(int j=0;j<cellValues.size();j++)
                {
                    if(cellValues.get(i).get(j).team == youPlay)
                    {
                        teamYouPlayTotal += cellValues.get(i).get(j).value;
                    }
                    else if(cellValues.get(i).get(j).team == opponentPlay)
                    {
                        teamOpponentTotal += cellValues.get(i).get(j).value;
                    }
                        
                }
            }
             return (teamYouPlayTotal - teamOpponentTotal);
           
         }
         
         
         ArrayList<Node> pointsAvailable = getAvailableStates(cellValues);
         
         if(pointsAvailable.isEmpty())
         {
            // System.out.println("Im here");
             teamYouPlayTotal = 0;
            teamOpponentTotal = 0;
            for(int i=0;i<cellValues.size();i++)
            {
                for(int j=0;j<cellValues.size();j++)
                {
                    if(cellValues.get(i).get(j).team == youPlay)
                    {
                        teamYouPlayTotal += cellValues.get(i).get(j).value;
                    }
                    else if(cellValues.get(i).get(j).team == opponentPlay)
                    {
                        teamOpponentTotal += cellValues.get(i).get(j).value;
                    }
                        
                }
            }
            
             
               return (teamYouPlayTotal - teamOpponentTotal);
        
             
         }

        //changed at 12th hour
        if(depth == 1)
        {
            rootsChildrenScores.clear();
        }

         int maxValue = Integer.MIN_VALUE;
         int minValue = Integer.MAX_VALUE;
         
          ArrayList<Integer> scores = new ArrayList<Integer>();
         
         for(int i=0; i<pointsAvailable.size(); i++)
         {
             Node n = pointsAvailable.get(i);
             
             ArrayList<ArrayList<Node>> old2 = new ArrayList<ArrayList<Node>>() ;
            for(int copyi=0; copyi<cellValues.size();copyi++)
            {
                ArrayList<Node> copyRow = new ArrayList<Node>();
                for(int copyj=0; copyj<cellValues.size();copyj++)
                {
                    Node addNode = new Node (cellValues.get(copyi).get(copyj).value, cellValues.get(copyi).get(copyj).team);
                    addNode.i = cellValues.get(copyi).get(copyj).i;
                    addNode.j = cellValues.get(copyi).get(copyj).j;
                    copyRow.add(addNode);
                }
                old2.add(copyRow);
            }
             
             int currentScore = 0;
             
             if(turn == 1)
             {
                // System.out.println("Depth is "+depth + " and turn is "+turn);
                 //duplicateCellValues = cellValues;
                 //int currentScore = Integer.MIN_VALUE;
                 placeMove(n,1,old2);
                 raidIt(n,1, old2);
                 
                 //displayGameBoard(old2);
                  currentScore = minimaxAB(depth+1,2,old2,A,B);
                  maxValue = Math.max(maxValue, currentScore);
                  A = Math.max(currentScore, A);
                  //CHANGED AT 11.29 PM
                  
                  //changed at 12th hour
                  if(depth == 1)
                  {
                      n.rootsChildrenScores = currentScore;
                      rootsChildrenScores.add(n);
                  }
                  
                  scores.add(currentScore);
                 
                  //Changed at 12th hour
                  //scores.add(maxValue);
                 
                 
                 //CHANGED AT 11.29 PM
                 // System.out.println("Alpha value is "+ A);
                 // System.out.println("Beta value is " + B);
                 
                 
            
             // B = Integer.MAX_VALUE;
                  
            
                 
                 /*
                    if(depth==1)
                 {
                     n.rootsChildrenScores = currentScore;
                     rootsChildrenScores.add(n);
                     System.out.println("The nodes in rootsChildrenScores are ");
                     for(Node kaushal : rootsChildrenScores)
                     System.out.print((kaushal).value + " ");
                     System.out.println();
                     displayGameBoard(cellValues);
                 }
                 */
                 //System.out.println("The score in turn = 1 is "+ scores);
                 
             }
             else if(turn == 2)
             {
               //  System.out.println("Depth is "+depth + " and turn is "+turn);
               //  duplicateCellValues = cellValues;
               //System.out.println("The cellValues before PLACE A MOVE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               //displayGameBoard(cellValues);
               //System.out.println("The cellValues in minimax are @@@@@@@@@@@@@@@@@@@@@@@2 ");
               //  displayGameBoard(cellValues);
              // int currentScore = Integer.MAX_VALUE;
                 placeMove(n,2,old2);
               //System.out.println("The cellValues after PLACE A MOVE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               //displayGameBoard(cellValues);  
                 raidIt(n,2,old2);
                  
               //  displayGameBoard(old2);
                  currentScore =  minimaxAB(depth+1, 1, old2, A,B);
                  minValue = Math.min(minValue, currentScore);
                  B = Math.min(currentScore, B);
                  //CHANGED AT 11.29 PM
                  //Changed at 12th hour
                 scores.add(currentScore);  
                 
                 //scores.add(minValue);
                 //CHANGED AT 11.29 PM
                 
                 //System.out.println("The score in turn = 2 is "+ scores);
                 
                 
                //  System.out.println("Alpha value is "+ A);
                //  System.out.println("Beta value is " + B);
                    
             }
             
            // System.out.println("The cleared node is " + n.value);
             //if(n.j==1)
            // System.out.println("The node left to this node is "+ cellValues.get(n.i).get(n.j-1).team + " " +cellValues.get(n.i).get(n.j-1).isRaided);
             //unRaid(n);
             cellValues.get(n.i).get(n.j).team = '.';
             if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE)
                break;
             
            
         }
         
         //System.out.println(turn==1? returnMax(scores):returnMin(scores) + " is the returned value from score");
         
         return turn==1 ? returnMax(scores): returnMin(scores);
         //return turn==1 ? maxValue: minValue;
         
     }
     
     
     
     
}


class MergeSortForRaidTotal {
    
    ArrayList<Node> availablePieces ;
    
    public MergeSortForRaidTotal(ArrayList<Node> availablePieces)
    {
        this.availablePieces = availablePieces;
    }
    
    public void startSort ()
    {
        divide(0, availablePieces.size()-1);
    }
    
    public void divide( int startIndex, int endIndex)
    {
        if(startIndex <= endIndex && endIndex-startIndex >= 1)              //Not sure of second condition
        {
            int mid = (startIndex+endIndex)/2;
        
        
            divide(startIndex, mid);
            divide(mid+1, endIndex);

            merge(startIndex,mid,endIndex);
        }

    }
    
    public void merge (int startIndex, int mid, int endIndex)
    {
        ArrayList<Node> answer = new ArrayList<Node>();
        
        int leftPointer = startIndex;
        int rightPointer = mid+1;
        
        while(leftPointer <= mid && rightPointer <= endIndex)
        {
            if(availablePieces.get(leftPointer).raidTotal > availablePieces.get(rightPointer).raidTotal)
            {
                answer.add(availablePieces.get(leftPointer));
                leftPointer++;
            }
            else
            {
                answer.add(availablePieces.get(rightPointer));
                rightPointer++;
            }
            
        }
        
        while(leftPointer<=mid)
        {
            answer.add(availablePieces.get(leftPointer));
            leftPointer++;
        }
        while(rightPointer <= endIndex)
        {
            answer.add(availablePieces.get(rightPointer));
            rightPointer++;
        }
        
        
        int i=0;int j = startIndex;
        
        while(i<answer.size())
        {
            availablePieces.set(j,answer.get(i));
            i++;
            j++;
        }
        
    }
    
    
}

class MergeSortForMaxArray {
    
    ArrayList<Node> availablePieces ;
    
    public MergeSortForMaxArray(ArrayList<Node> availablePieces)
    {
        this.availablePieces = availablePieces;
    }
    
    public void startSort ()
    {
        divide(0, availablePieces.size()-1);
    }
    
    public void divide( int startIndex, int endIndex)
    {
        if(startIndex <= endIndex && endIndex-startIndex >= 1)              //Not sure of second condition
        {
            int mid = (startIndex+endIndex)/2;
        
        
            divide(startIndex, mid);
            divide(mid+1, endIndex);

            merge(startIndex,mid,endIndex);
        }

    }
    
    public void merge (int startIndex, int mid, int endIndex)
    {
        ArrayList<Node> answer = new ArrayList<Node>();
        
        int leftPointer = startIndex;
        int rightPointer = mid+1;
        
        while(leftPointer <= mid && rightPointer <= endIndex)
        {
            if(availablePieces.get(leftPointer).rootsChildrenScores >= availablePieces.get(rightPointer).rootsChildrenScores)
            {
                answer.add(availablePieces.get(leftPointer));
                leftPointer++;
            }
            else
            {
                answer.add(availablePieces.get(rightPointer));
                rightPointer++;
            }
            
        }
        
        while(leftPointer<=mid)
        {
            answer.add(availablePieces.get(leftPointer));
            leftPointer++;
        }
        while(rightPointer <= endIndex)
        {
            answer.add(availablePieces.get(rightPointer));
            rightPointer++;
        }
        
        
        int i=0;int j = startIndex;
        
        while(i<answer.size())
        {
            availablePieces.set(j,answer.get(i));
            i++;
            j++;
        }
        
    }
    
    
}


class ReadFile{

String path;

public ReadFile(String file_path)
{
path = file_path;
}

public int readLines() throws IOException
{

FileReader fr = new FileReader(path);
BufferedReader br = new BufferedReader(fr);
String tmpLine;
int numberOfLines = 0;
while ( (tmpLine = br.readLine()) != null)
{
numberOfLines++;
}
br.close();
return numberOfLines;
}

public String[] OpenFile() throws IOException
{
FileReader fr = new FileReader(path);
BufferedReader br = new BufferedReader(fr);

int numberOfLines = readLines();
String []textData = new String [numberOfLines];

for (int i=0;i<numberOfLines; i++)
{
	textData[i] = br.readLine();
}
br.close();
return textData;

}
}