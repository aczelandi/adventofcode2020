#!/bin/zsh

bag_color="shiny gold"

input="$(cat input.txt)"

get_bags_that_can_fit_bag() {
  input="$1"
  bag_color="$2"
  rows_with_my_bag="$(echo "$input" | grep "$bag_color" | grep -v "^$bag_color")"
  bags_that_can_fit_this_bag=$(echo "$rows_with_my_bag" | cut -d ' ' -f1-2)

  echo "$bags_that_can_fit_this_bag"
}

visited_bags="visited_bags.txt"

touch "$visited_bags"

find_all_bags() {
  bag_color=$1
  echo "Bag color to check: $bag_color"

  bags_that_can_fit_this_bag=$(get_bags_that_can_fit_bag "$input" "$bag_color")
  echo "Bags that can fit bag: $bags_that_can_fit_this_bag"

  if [[ -n $bags_that_can_fit_this_bag ]]; then
  while read -r line ; do
    echo "Color: $line"
    bag_is_visited="$(cat "$visited_bags" | grep "$line")"

    if [[ -z $bag_is_visited ]]; then
      echo "$line" >> "$visited_bags"
      find_all_bags "$line"
    fi
  done <<< "$bags_that_can_fit_this_bag"
  fi
}

find_all_bags "$bag_color"

echo "Count of distinct bags that can fit shiny gold: $(wc -l $visited_bags)"

rm "$visited_bags"