  static Integer recursiveGetMin(int[] a) {
    
    if(a.length == 0) {
      return null;
    }
    
    int min = a[0];
    
    if(a.length == 1) {
      return min;
    }
    
    if(min > a[1]) {
      getMin(Arrays.copyOfRange(a,1,a.length));
    }
    
    else {
      int temp = a[1];
      a[1] = min;
      a[0] = temp;
      min = a[0];
    }
    
    if(a.length == 2) {
      return min < a[1] ? min : a[1];
    }
    
    return getMin(Arrays.copyOfRange(a,1,a.length));
  }
