package control;

public class ControlOrdenaciones {
	
	public static void quicksort(int [] a , int inicio , int fin) {
		
		int pivote = a[inicio];
		int i = inicio;
		int j = fin;
		int aux;
		
		while(i < j) {
			while(a[i] <= pivote) i++;
			while(a[j] > pivote) j--;
			if(i < j) {
				aux = a[i];
				a[i] = a[j];
				a[j] = aux;
			}
		}
		
		a[inicio] = a[j];
		a[j] = pivote;
		
		if(inicio < j-1) {
			quicksort(a,inicio, j-1);
		}
		
		if(j+1 < fin) {
			quicksort(a,j+1,fin);
		}
	}
	
	
}
