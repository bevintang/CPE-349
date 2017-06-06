public class Item implements Comparable <Item> {
   private int index;
   private int value;
   private int weight;

   public Item(int index, int value, int weight) {
      this.index = index;
      this.value = value;
      this.weight = weight;
   }

   public int getIndex() {
       return index;
   }

   public int getValue() {
      return value;
   }

   public int getWeight() {
      return weight;
   }

   public int compareTo(Item other) {
      return index = other.getIndex(); // Ascending order of index number
   }
}
