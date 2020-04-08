package calculator;

import java.util.ArrayList;

/**
 * A Farey sequence of any order (I don't recommend going higher than 500).
 * 
 * order = 1: 0/1,                                                        1/1
 * order = 2: 0/1,                          1/2,                          1/1
 * order = 3: 0/1,                1/3,      1/2,      2/3,                1/1
 * order = 4: 0/1,           1/4, 1/3,      1/2,      2/3, 3/4,           1/1
 * order = 5: 0/1,      1/5, 1/4, 1/3, 2/5, 1/2, 3/5, 2/3, 3/4, 4/5,      1/1
 * order = 6: 0/1, 1/6, 1/5, 1/4, 1/3, 2/5, 1/2, 3/5, 2/3, 3/4, 4/5, 5/6, 1/1
 * 
 * @author Ethan Davis
 * @version 4/5/19
 */
public class FareySequence
{
  private ArrayList<Fraction> sequence;
  private int order;

  /**
   * Constructs a Farey sequence.
   * 
   * @param order
   *          order of the sequence
   */
  public FareySequence(int order)
  {
    if (order < 1)
      this.order = 1;
    else
      this.order = order;

    sequence = new ArrayList<Fraction>();
    calculateSequence(this.order);
  }

  /**
   * Recursively sets up the sequence by calculation the sequence of the one less order and then
   * inserting the new fractions that come with this order.
   * 
   * @param or
   *          order of the sequence
   */
  private void calculateSequence(int or)
  {
    // create farey sequence of order 1
    if (or == 1)
    {
      sequence.add(new Fraction(0, 1, true));
      sequence.add(new Fraction(1, 1, true));
    }
    else
    {
      // create sequence of lower orders
      calculateSequence(or - 1);

      // insert fractions whose denominator = order
      for (int num = 1; num < or; num++)
      {
        Fraction nextFraction = new Fraction(num, or, true);
        int index = 1;

        while (index < sequence.size())
        {
          // IF i - 1 < nextF < i THEN insert nextF at i, rest of sequence gets pushed right
          if (nextFraction.compareTo(sequence.get(index - 1)) > 0
              && nextFraction.compareTo(sequence.get(index)) < 0)
          {
            sequence.add(index, nextFraction);
            // break
            index = sequence.size();
          }
          else
          {
            index++;
          }
        }
      }
    }
  }

  /**
   * Gets the fraction at said index.
   * 
   * @param index
   *          location of the fraction
   * @return the index
   */
  public Fraction get(int index)
  {
    return sequence.get(index);
  }

  /**
   * Gets the fraction at the next index.
   * 
   * @param previous
   *          fraction to search after
   * @return the next fraction
   */
  public Fraction getNext(Fraction previous)
  {
    int prevIndex = search(previous);
    int nextIndex;

    // no further element
    if (prevIndex == sequence.size() - 1)
      nextIndex = 0;
    else
      nextIndex = prevIndex + 1;

    return sequence.get(nextIndex);
  }

  /**
   * Performs a binary search on this sequence for a fraction index.
   * 
   * @param x
   *          Fraction being searched for
   * @return index of fraction, -1 if no fraction exists
   */
  public int search(Fraction x)
  {
    int l = 0, r = sequence.size() - 1;
    while (l <= r)
    {
      int m = l + (r - l) / 2;

      if (sequence.get(m).getValue() == x.getValue())
        return m;
      if (sequence.get(m).getValue() < x.getValue())
        l = m + 1;
      else
        r = m - 1;
    }

    return -1;
  }

  /**
   * Gets the size of the sequence.
   * 
   * @return size
   */
  public int size()
  {
    return sequence.size();
  }
}
