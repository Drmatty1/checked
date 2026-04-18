class Solution {
    int solA(int []arr, int curr, int i, int t){
        
        if(i>=arr.length){
            if( curr == t )return 1;
            return 0;
        }
        int a = solA(arr,curr-arr[i],i+1,t);
        int b = solA(arr,curr+arr[i],i+1,t);
        return a+b;
    }

    int solA1(int []arr, int curr, int i, int t,int [][]dp){
        
        if(i>=arr.length){
            if( curr == t )return 1;
            return 0;
        }
        int m=1001;
        if(dp[i][curr+m]!=-1)return dp[i][curr+m];
        int a = solA1(arr,curr-arr[i],i+1,t,dp);
        int b = solA1(arr,curr+arr[i],i+1,t,dp);
        return dp[i][curr+m]=a+b;
    }


    int solB(int []arr, int curr, int i, int t){
        
         if(i>=arr.length){
            if( curr == t )return 1;
            return 0;
        }
    
        int a = solB(arr,curr,i+1,t);
        int b = solB(arr,curr+arr[i],i+1,t);

        return a+b;
    }

    int solB1(int []arr, int curr, int i, int t,int[][]dp){
        
        if(i>=arr.length){
            if( curr == t )return 1;
            return 0;
        }

        if(dp[i][curr] != -1)return dp[i][curr];
    
        int a = solB1(arr,curr,i+1,t,dp);
        int b = solB1(arr,curr+arr[i],i+1,t,dp);

        return dp[i][curr] = a+b;
    }
    
    
    int solB2(int []arr, int target){
        
        int n = arr.length;

        int totalSum=0;
        for(int i=0;i<n;i++)totalSum+=arr[i];
        if( (totalSum+target)%2 != 0 )return 0;
        int P = (target+totalSum)/2;
        if( P < 0 ) return 0;

        int [][]dp = new int[n+1][P+1];
        dp[n][P]=1;

        for( int i=n-1; i>=0; i-- ){
            for( int curr = P; curr>=0; curr-- ){
                
                int a = dp[i+1][curr];
                int b = 0;
                if(curr+arr[i]<=P)
                    b = dp[i+1][arr[i]+curr];

                dp[i][curr] = a+b;
            }
        }
        
        return dp[0][0];
    }


    int solB3(int []arr, int target){
        
        int n = arr.length;

        int totalSum=0;
        for(int i=0;i<n;i++)totalSum+=arr[i];
        if( (totalSum+target)%2 != 0 )return 0;
        int P = (target+totalSum)/2;
        if( P < 0 ) return 0;

        int []next = new int[P+1];
        next[P]=1;

        for( int i=n-1; i>=0; i-- ){
            
            // here Direction does not matter
            // since we are modifing next array in-place
            // moving curr from P to 0 won't work;
            for( int curr = 0; curr<=P; curr++ ){
                
                int a = next[curr];
                int b = 0;
                if(curr+arr[i]<=P)
                    b = next[arr[i]+curr];

                next[curr] = a+b;
            }
        
        }
        
        return next[0];
    }
    

    public int findTargetSumWays(int[] nums, int target) {         
        int n = nums.length;

        // return solA(nums,0,0,target);
        
        // int [][]dp = new int[n][1001+1001];
        // for(int i=0;i<n;i++)Arrays.fill(dp[i],-1);
        // return solA1(nums,0,0,target,dp);



        // **** converting it to subset sun=target ****

        // P-N=target, P+N=totalsum 
        // 2P = target + totalSum
        // P = (target+totalSum)/2;
        // (totalSum+target)%2==0 must !! (P>=0) must

        // int totalSum=0;
        // for(int i=0;i<n;i++)totalSum+=nums[i];
        // if( (totalSum+target)%2 != 0 )return 0;
        // int P = (target+totalSum)/2;
        // if( P < 0 ) return 0;

        // ***M-1***
        // return solB(nums,0,0,P);

        // ***M-2***
        // int [][]dp = new int[n][1001];
        // for(int i=0;i<n;i++)Arrays.fill(dp[i],-1);
        //  return solB1(nums,0,0,P,dp);

        // ***M-3***
        // return solB2(nums,target);

        // ***M-4***
        return solB3(nums,target);
        
    }
}
